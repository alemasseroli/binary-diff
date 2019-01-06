package binarydiff;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.difference;
import static org.apache.commons.lang3.StringUtils.indexOfDifference;

class BinaryDiffService {

    private String left;
    private String right;

    public String diff() {
        String diff;
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

    public String left() {
        return left;
    }

    public String right() {
        return right;
    }

    void setLeft(String left) {
        this.left = left;
    }

    void setRight(String right) {
        this.right = right;
    }
}
