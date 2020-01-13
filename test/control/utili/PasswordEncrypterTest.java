package control.utili;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordEncrypterTest {

    @Test
    void criptaPassword1() {
        String passowrd = "ProvaPassword10";
        String expected = "e8188c7495d60bda94913c41724e9f7c729331598728b3b475be54f07fe41415";
        assertEquals(expected, PasswordEncrypter.criptaPassword(passowrd));
    }

    @Test
    void criptaPassword2() {
        String passowrd = "";
        String expected = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
        assertEquals(expected, PasswordEncrypter.criptaPassword(passowrd));
    }

    @Test
    void criptaPassword3() {
        String passowrd = "Qu3s7aSaRÀd1fficile!da@estrApoLare";
        String expected = "42aaefabbdd00a4fef1176216309dbcf93a577d83ca30b69349d2ec92a844b9e";
        assertEquals(expected, PasswordEncrypter.criptaPassword(passowrd));
    }
}