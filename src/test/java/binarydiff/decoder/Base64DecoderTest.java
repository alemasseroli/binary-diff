package binarydiff.decoder;

import org.junit.Test;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class Base64DecoderTest {

    @Test
    public void testDecodeBase64() {
        Base64Decoder decoder = new Base64Decoder();
        final String originalElement = "String to encode and decode!";
        final String encodedElement = Base64.getEncoder().encodeToString(originalElement.getBytes());

        final String decodedElement = decoder.decodeBase64(encodedElement);
        assertThat(decodedElement).isEqualTo(originalElement);
    }
}
