package binarydiff;

import java.util.HashMap;
import java.util.Map;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.difference;
import static org.apache.commons.lang3.StringUtils.indexOfDifference;

class BinaryDiffService {

    private Map<String, String> leftMembers = new HashMap<>();
    private Map<String, String> rightMembers = new HashMap<>();

    public String diff(String id) {
        final String left = left(id);
        final String right = right(id);
        if (left.equals(right)) {
            return "Values are equal!";
        } else if (left.length() != right.length()) {
            return "Values have different sizes";
        } else {
            final int diffLength = difference(left, right).length();
            final int diffIndex = indexOfDifference(left, right);
            return format("Values differ in {0} chars at position: {1}", diffLength, diffIndex);
        }
    }

    public String left(String id) {
        return leftMembers.get(id);
    }

    public String right(String id) {
        return rightMembers.get(id);
    }

    void setLeft(String id, String left) {
        this.leftMembers.put(id, left);
    }

    void setRight(String id, String right) {
        this.rightMembers.put(id, right);
    }
}
