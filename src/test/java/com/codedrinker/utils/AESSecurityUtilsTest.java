package com.codedrinker.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by codedrinker on 18/08/2017.
 */
public class AESSecurityUtilsTest {
    @Test
    public void encrypt() throws Exception {
        String code = "UstyI8JoQOty8egSMFQfig==";
        String codedrinker = AESSecurityUtil.encrypt("codedrinker", code);
        System.out.println(codedrinker);

        String decrypt = AESSecurityUtil.decrypt(codedrinker, code);
        System.out.println(decrypt);
        Assert.assertEquals("codedrinker", decrypt);
    }
}
