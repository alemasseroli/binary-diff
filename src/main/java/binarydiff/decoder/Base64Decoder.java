package binarydiff.decoder;

import java.util.Base64;
import java.util.Base64.Decoder;

public class Base64Decoder {

    private static Decoder decoder = Base64.getDecoder();

    public String decodeBase64(String encoded) {
        return new String(decoder.decode(encoded.getBytes()));
    }

}
