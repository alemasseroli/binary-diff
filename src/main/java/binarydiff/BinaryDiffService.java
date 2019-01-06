package binarydiff;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.difference;
import static org.apache.commons.lang3.StringUtils.indexOfDifference;

class BinaryDiffService {

    private Map<String, String> leftMembers = new ConcurrentHashMap<>();
    private Map<String, String> rightMembers = new ConcurrentHashMap<>();

    public String diff(String id) {
        String diff;
        final String left = left(id);
        final String right = right(id);
        if (left.equals(right)) {
            diff = "Values are equal!";
        } else if (left.length() != right.length()) {
            diff = "Values have different sizes.";
        } else {
            final int diffLength = difference(left, right).length();
            final int diffIndex = indexOfDifference(left, right);
            diff = format("Values differ in {0} chars at position: {1}", diffLength, diffIndex);
        }
        return diff;
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
