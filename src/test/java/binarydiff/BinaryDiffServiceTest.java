package binarydiff;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryDiffServiceTest {

    private BinaryDiffService service;
    private final String id = "id";

    @Before
    public void setUp() {
        service = new BinaryDiffService();
    }

    @Test
    public void testEqualMembersDiff() {
        service.setLeft(id, "Equal members");
        service.setRight(id, "Equal members");
        assertThat(service.diff(id)).isEqualTo("Values are equal!");
    }

    @Test
    public void testDifferentSizesDiff() {
        service.setLeft(id, "Members have different size");
        service.setRight(id, "abc");
        assertThat(service.diff(id)).isEqualTo("Values have different sizes");
    }

    @Test
    public void testDiff() {
        service.setLeft(id, "Member number one");
        service.setRight(id, "Member number two");
        assertThat(service.diff(id)).isEqualTo("Values differ in 3 chars at position: 14");
    }
}
