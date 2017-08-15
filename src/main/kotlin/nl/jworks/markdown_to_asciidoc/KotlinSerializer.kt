package nl.jworks.markdown_to_asciidoc

import nl.jworks.markdown_to_asciidoc.code.Linguist
import nl.jworks.markdown_to_asciidoc.html.TableToAsciiDoc

import nl.jworks.markdown_to_asciidoc.util.Joiner
import org.parboiled.common.StringUtils
import org.pegdown.LinkRenderer
import org.pegdown.Printer
import org.pegdown.ast.*

import java.util.*

import org.parboiled.common.Preconditions.checkArgNotNull

class ToAsciiDocSerializerKt @JvmOverloads constructor(private var rootNode: RootNode, protected var source: String? = null) : Visitor {

    protected var printer = Printer()
    protected val references: MutableMap<String, ReferenceNode> = HashMap()
    protected val abbreviations: MutableMap<String, String> = HashMap()
    protected val linkRenderer = LinkRenderer()

    protected var currentTableNode: TableNode? = null
    protected var currentTableColumn: Int = 0
    protected var inTableHeader: Boolean = false

    protected var listMarker: Char = ' '
    protected var listLevel = 0
    protected var blockQuoteLevel = 0

    // Experimental feature.
    protected var autoDetectLanguageType: Boolean = false
    protected var linguist = Linguist()

    init {
        this.printer = Printer()
        this.linguist = Linguist()
        this.autoDetectLanguageType = false
        checkArgNotNull(rootNode, "rootNode")
    }

    fun toAsciiDoc(): String {
        cleanAst(rootNode)
        rootNode.accept(this)
        val result = normalizeWhitelines(printer.string)
        printer.clear()
        return result
    }

    override fun visit(node: RootNode) {
        for (refNode in node.references) {
            visitChildren(refNode)
            references.put(normalize(printer.string), refNode)
            printer.clear()
        }
        for (abbrNode in node.abbreviations) {
            visitChildren(abbrNode)
            val abbr = printer.string
            printer.clear()
            abbrNode.expansion.accept(this)
            val expansion = printer.string
            abbreviations.put(abbr, expansion)
            printer.clear()
        }
        visitChildren(node)
    }

    override fun visit(node: AbbreviationNode) {}

    override fun visit(node: AnchorLinkNode) {
        printer.print(node.text)
    }

    override fun visit(node: AutoLinkNode) {
        printLink(linkRenderer.render(node))
    }

    override fun visit(node: BlockQuoteNode) {
        printer.println().println()

        blockQuoteLevel += 4

        repeat('_', blockQuoteLevel)
        printer.println()
        visitChildren(node)
        printer.println().println()
        repeat('_', blockQuoteLevel)

        blockQuoteLevel -= 4

        printer.println()
    }

    override fun visit(node: BulletListNode) {
        val prevListMarker = listMarker
        listMarker = '*'

        listLevel += 1
        visitChildren(node)
        listLevel -= 1

        listMarker = prevListMarker
    }

    override fun visit(node: CodeNode) {
        printer.print('`')
        printer.printEncoded(node.text)
        printer.print('`')
    }

    override fun visit(node: DefinitionListNode) {
        printer.println()
        visitChildren(node)
    }

    override fun visit(node: DefinitionTermNode) {
        visitChildren(node)
        printer.indent(2)
        printer.print("::").println()
    }

    override fun visit(node: DefinitionNode) {
        visitChildren(node)
        if (printer.indent > 0) {
            printer.indent(-2)
        }
        printer.println()
    }

    override fun visit(node: ExpImageNode) {
        val text = printChildrenToString(node)
        val imageRenderer = linkRenderer.render(node, text)
        val linkNode: Node? = findParentNode(node, rootNode)
        if (linkNode is ExpLinkNode) {
            printImageTagWithLink(imageRenderer, linkRenderer.render(linkNode, null))
        } else {
            printImageTag(linkRenderer.render(node, text))
        }
    }

    override fun visit(node: ExpLinkNode) {
        val text = printChildrenToString(node)
        if (text.startsWith("image:")) {
            printer.print(text)
        } else {
            printLink(linkRenderer.render(node, text))
        }
    }

    override fun visit(node: HeaderNode) {
        printer.println().println()
        repeat('=', node.level)
        printer.print(' ')
        visitChildren(node)
        printer.println().println()
    }

    private fun repeat(c: Char, times: Int) {
        for (i in 0 until times) {
            printer.print(c)
        }
    }

    override fun visit(node: HtmlBlockNode) {
        val text: String = node.text
        if (text.isNotEmpty()) printer.println()
        if (text.startsWith("<table")) {
            printer.print(TableToAsciiDoc.convert(text))
            printer.println()
        }
    }

    override fun visit(node: InlineHtmlNode) {
        printer.print(node.text)
    }

    override fun visit(node: ListItemNode) {
        printer.println()
        repeat(listMarker, listLevel)
        printer.print(" ")

        visitChildren(node)
    }

    override fun visit(node: MailLinkNode) {
        printLink(linkRenderer.render(node))
    }

    override fun visit(node: OrderedListNode) {
        val prevListMarker = listMarker
        listMarker = '.'

        listLevel += 1
        visitChildren(node)
        listLevel -= 1

        listMarker = prevListMarker
    }

    override fun visit(node: ParaNode) {
        if (!isListItemText(node)) {
            printer.println().println()
        }
        visitChildren(node)
        printer.println().println()
    }

    override fun visit(node: QuotedNode) {
        when (node.type!!) {
            QuotedNode.Type.DoubleAngle -> {
                printer.print("«")
                visitChildren(node)
                printer.print("»")
            }
            QuotedNode.Type.Double -> {
                printer.print("\"")
                visitChildren(node)
                printer.print("\"")
            }
            QuotedNode.Type.Single -> {
                printer.print("'")
                visitChildren(node)
                printer.print("'")
            }
        }
    }

    override fun visit(node: ReferenceNode) {
        // reference nodes are not printed
    }

    override fun visit(node: RefImageNode) {
        val text = printChildrenToString(node)
        val key = if (node.referenceKey != null) printChildrenToString(node.referenceKey) else text
        val refNode = references[normalize(key)]
        if (refNode == null) { // "fake" reference image link
            printer.print("![").print(text).print(']')
            if (node.separatorSpace != null) {
                printer.print(node.separatorSpace).print('[')
                if (node.referenceKey != null) printer.print(key)
                printer.print(']')
            }
        } else
            printImageTag(linkRenderer.render(node, refNode.url, refNode.title, text))
    }

    override fun visit(node: RefLinkNode) {
        val text = printChildrenToString(node)
        val key = if (node.referenceKey != null) printChildrenToString(node.referenceKey) else text
        val refNode = references[normalize(key)]
        if (refNode == null) { // "fake" reference link
            printer.print('[').print(text).print(']')
            if (node.separatorSpace != null) {
                printer.print(node.separatorSpace).print('[')
                if (node.referenceKey != null) printer.print(key)
                printer.print(']')
            }
        } else
            printLink(linkRenderer.render(node, refNode.url, refNode.title, text))
    }

    override fun visit(node: SimpleNode) {
        when (node.type) {
            SimpleNode.Type.Apostrophe -> printer.print("'")
            SimpleNode.Type.Ellipsis -> printer.print("…")
            SimpleNode.Type.Emdash -> printer.print("—")
            SimpleNode.Type.Endash -> printer.print("–")
            SimpleNode.Type.HRule -> printer.println().print("'''")
            SimpleNode.Type.Linebreak ->
                // look for length of span to detect hard line break (2 trailing spaces plus endline)
                // necessary because Pegdown doesn't distinguish between a hard line break and a normal line break
                if (source != null && source!!.substring(node.startIndex, node.endIndex).startsWith(HARD_LINE_BREAK_MARKDOWN)) {
                    printer.print(" +").println()
                } else {
                    // QUESTION should we fold or preserve soft line breaks? (pandoc emits a space here)
                    printer.println()
                }
            SimpleNode.Type.Nbsp -> printer.print("{nbsp}")
            else -> throw IllegalStateException()
        }
    }

    override fun visit(node: StrongEmphSuperNode) {
        if (node.isClosed) {
            if (node.isStrong) {
                printNodeSurroundedBy(node, "*")
            } else {
                printNodeSurroundedBy(node, "_")
            }
        } else {
            //sequence was not closed, treat open chars as ordinary chars
            printer.print(node.chars)
            visitChildren(node)
        }
    }

    override fun visit(node: StrikeNode) {
        printer.print("[line-through]").print('#')
        visitChildren(node)
        printer.print('#')
    }

    override fun visit(node: TableBodyNode) {
        visitChildren(node)
    }

    override fun visit(node: TableCaptionNode) {
        printer.println().print("<caption>")
        visitChildren(node)
        printer.print("</caption>")
    }

    override fun visit(node: TableCellNode) {
        val columns = currentTableNode!!.columns
        val column = columns[Math.min(currentTableColumn, columns.size - 1)]

        val pstr = printer.string
        if (pstr.isNotEmpty()) {
            if (pstr.endsWith("\n") || pstr.endsWith(" ")) {
                printer.print("|")
            } else {
                printer.print(" |")
            }
        } else {
            printer.print("|")
        }
        column.accept(this)
        if (node.colSpan > 1) printer.print(" colspan=\"").print(Integer.toString(node.colSpan)).print('"')
        visitChildren(node)

        currentTableColumn += node.colSpan
    }

    override fun visit(node: TableColumnNode) {
        // nothing here yet
    }

    override fun visit(node: TableHeaderNode) {
        inTableHeader = true
        visitChildren(node)
        inTableHeader = false
    }

    private fun ifColumnsHaveAlignmentSpecified(columns: List<TableColumnNode>) = columns.any { it.alignment != TableColumnNode.Alignment.None }

    private fun getColumnAlignment(columns: List<TableColumnNode>): String {

        val result = ArrayList<String>()

        for (column in columns) {
            when (column.alignment) {
                TableColumnNode.Alignment.None, TableColumnNode.Alignment.Left -> result.add("<")
                TableColumnNode.Alignment.Right -> result.add(">")
                TableColumnNode.Alignment.Center -> result.add("^")
                else -> throw IllegalStateException()
            }
        }

        return Joiner.join(result, ",")
    }

    override fun visit(node: TableNode) {
        currentTableNode = node

        val columns = node.columns

        if (ifColumnsHaveAlignmentSpecified(columns)) {
            printer.print("[cols=\"")
            printer.print(getColumnAlignment(columns))
            printer.print("\"]")
            printer.println()
        }

        printer.print("|===")
        visitChildren(node)
        printer.println()
        printer.print("|===")
        printer.println()

        currentTableNode = null
    }

    override fun visit(node: TableRowNode) {
        currentTableColumn = 0

        printer.println()

        visitChildren(node)

        if (inTableHeader) {
            printer.println()
        }
    }

    override fun visit(node: VerbatimNode) {
        printer.println()

        var type = node.type
        val text = node.text

        if (autoDetectLanguageType) {
            type = linguist.detectLanguage(text)
        }

        if (!type.isEmpty()) {
            printer.print("[source,$type]")
        }

        printer.println()
        repeat('-', 4)
        printer.println()
        printer.print(text)
        repeat('-', 4)
        printer.println().println()
    }

    override fun visit(node: WikiLinkNode) = printLink(linkRenderer.render(node))

    override fun visit(node: TextNode) {
        if (abbreviations.isEmpty()) {
            printer.print(node.text)
        } else {
            printWithAbbreviations(node.text)
        }
    }

    override fun visit(node: SpecialTextNode) {
        printer.printEncoded(node.text)
    }

    override fun visit(node: SuperNode) = visitChildren(node)

    override fun visit(node: Node) = throw RuntimeException("Don't know how to handle node " + node)

    // helpers
    protected fun visitChildren(node: AbstractNode) = node.children.forEach { it.accept(this) }

    /**
     * Removes superfluous nodes from the tree.
     */
    protected fun cleanAst(node: Node) {
        val children = node.children
        var i = 0
        val len = children.size
        while (i < len) {
            val c = children[i]
            if (c is RootNode) {
                children[i] = c.getChildren()[0]
            } else if (c.javaClass == SuperNode::class.java && c.children.size == 1) {
                children[i] = c.children[0]
            }

            cleanAst(c)
            i++
        }
    }

    protected fun printNodeSurroundedBy(node: AbstractNode, token: String) {
        printer.print(token)
        visitChildren(node)
        printer.print(token)
    }

    protected fun printImageTag(rendering: LinkRenderer.Rendering) {
        printer.print("image:")
        printer.print(rendering.href)
        printer.print('[')
        printTextWithQuotesIfNeeded(printer, rendering.text)
        printer.print(']')
    }

    protected fun printImageTagWithLink(image: LinkRenderer.Rendering, link: LinkRenderer.Rendering) {
        printer.print("image:").print(image.href).print('[')
        if (image.text != null && !image.text.isEmpty()) {
            printTextWithQuotesIfNeeded(printer, image.text)
            printer.print(',')
        }

        printer.print("link=").print(link.href).print(']')
    }

    protected fun printLink(rendering: LinkRenderer.Rendering) {
        var uri = rendering.href
        val text = rendering.text

        if (uri.startsWith("#")) {
            printer.print("<<").print(uri.substring(1)).print(',').print(text).print(">>")
        } else {
            if (!uri.contains("://")) {
                uri = "link:" + uri
            }
            printer.print(uri)
            if (uri != text) {
                printer.print('[')
                printTextWithQuotesIfNeeded(printer, rendering.text)
                printer.print(']')
            }
        }
    }

    protected fun printChildrenToString(node: SuperNode): String {
        val priorPrinter = printer
        printer = Printer()
        visitChildren(node)
        val result = printer.string
        printer = priorPrinter
        return result
    }

    protected fun normalize(string: String): String {
        val sb = StringBuilder()
        loop@ for (i in 0 until string.length) {
            val c = string[i]
            when (c) {
                ' ', '\n', '\t' -> continue@loop
            }
            sb.append(Character.toLowerCase(c))
        }
        return sb.toString()
    }

    protected fun normalizeWhitelines(text: String): String {
        // replace all double or more empty lines with single empty lines
        return text.replace("(?m)^[ \t]*\r?\n{2,}".toRegex(), "\n").trim { it <= ' ' }
    }

    protected fun printTextWithQuotesIfNeeded(p: Printer, text: String?) {
        if (text != null && !text.isEmpty()) {
            if (text.contains(",")) {
                p.print('"').print(text).print('"')
            } else {
                p.print(text)
            }
        }
    }

    protected fun printWithAbbreviations(string: String) {
        var expansions: MutableMap<Int, Map.Entry<String, String>>? = null

        for (entry in abbreviations.entries) {
            // first check, whether we have a legal match
            val abbr = entry.key

            var ix = 0
            while (true) {
                val sx = string.indexOf(abbr, ix)
                if (sx == -1) break

                // only allow whole word matches
                ix = sx + abbr.length

                if (sx > 0 && Character.isLetterOrDigit(string[sx - 1])) continue
                if (ix < string.length && Character.isLetterOrDigit(string[ix])) {
                    continue
                }

                // ok, legal match so save an expansions "task" for all matches
                if (expansions == null) {
                    expansions = TreeMap()
                }
                expansions.put(sx, entry)
            }
        }

        if (expansions != null) {
            var ix = 0
            for ((sx, value) in expansions) {
                val abbr = value.key
                val expansion = value.value

                printer.printEncoded(string.substring(ix, sx))
                printer.print("<abbr")
                if (StringUtils.isNotEmpty(expansion)) {
                    printer.print(" title=\"")
                    printer.printEncoded(expansion)
                    printer.print('"')
                }
                printer.print('>')
                printer.printEncoded(abbr)
                printer.print("</abbr>")
                ix = sx + abbr.length
            }
            printer.print(string.substring(ix))
        } else {
            printer.print(string)
        }
    }

    protected fun findParentNode(target: Node, from: Node): Node? {
        if (target == rootNode) {
            return null
        }

        for (c in from.children) {
            val candidate: Node? = findParentNode(target, c)

            if (target == c) {
                return from
            } else if (candidate != null) {
                return candidate
            }
        }

        return null
    }

    protected fun isFirstChild(parent: Node, child: Node): Boolean {
        return child == parent.children[0]
    }

    protected fun isListItemText(node: Node): Boolean {
        return if (listLevel == 0) {
            false
        } else {
            val parent = findParentNode(node, rootNode)
            parent is ListItemNode && isFirstChild(parent, node)
        }
    }

    companion object {
        val HARD_LINE_BREAK_MARKDOWN = "  \n"
    }
}
