package nl.jworks.markdown_to_asciidoc.util;

import java.util.List;

public final class Joiner {

    private Joiner() throws InstantiationException {
        throw new InstantiationException("This utility class is not created for instantiation");
    }

    public static String join(List<?> list, String delim) {
        int len = list.size();
        if (len == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(list.get(0).toString());
        for (int i = 1; i < len; i++) {
            sb.append(delim);
            sb.append(list.get(i).toString());
        }
        return sb.toString();
    }
}
