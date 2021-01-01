package com.yiyun.yiyuncarservice.utils;

import java.util.Map;

/**
 * @author hxx
 * @version 1.0
 * @title: test
 * @projectName ytcxxcx
 * @description: TODO
 * @date 2020/5/31 7:01
 */
public class test {
  public static void main(String[] args) throws Exception {
    String refundXml =
        "<xml><return_code>SUCCESS</return_code><appid><![CDATA[wx37e45ed88f51bcfd]]></appid><mch_id><![CDATA[1237457002]]></mch_id><nonce_str><![CDATA[e4554a1a91b8321f97d322b462cc2be7]]></nonce_str><req_info><![CDATA[AUzul+8g702ol8wjx0KTj3iol2EUZ/FUlapMKPeXxlp88zr1Plb5qOyL02nvuU5QG4QKpKqzmaZOBz8ZMcKiW2b3g9S9jukwHhveIMFx0tnZmWyKfCjXYPqc8EfCBVmlq6wT713bSF/PsVcxt1+zqhFlM0Ya/im36Csbs+enixjMvfcVCJW8DcBVW4kHji4iDtV9ez4D6lULANbvvschifgloNC0ZCGHly4qU0Tl1ma5NLeKCowAJki+qURDiyYCzkA23aJGosmo0G/tDVHPjXM7q3CAz2bz/FopeNfbUceedhmmEQttPPD+sBNTPz0MNrhTTrSSX8oUQSHbVRUXlgP4CfiJvc7scGbGQiBwIj34fljO5cyWXKkZoc8E6DtLy2S/2detnEkCm/KXcItGFFAwntl7Zsw1QMJvSO++AtighA+XfD5swpAlHTy8i8JkxzsWEdltUhrsDQ/qkMvA1BdpsxXzxl8s9Dl/0Wku5vu0WyvzW4mcZXt0FBCv0hpGZVb8RTUG/2EzSjwHU5J8raIkM7qmZ6dsYoA1FgIvbk6UtrG9Mgk7dHijLegObUqqrfwBOIkU3NIQB3AewGQB3D9xUzdufQ2VcMqfIXCS6kwp9t+nX9ervnt4CK0xGMgK/vDu49PjgD9+JrPo3TmqlcJOBa19FDxIKXAqm43Lm0uz2uBSRFVv0YIRc9i2yhlwk8N4s1fdr/uRSamMdnk7yWpBAkamn3YBKyJJ2Np1suqSNr/CXxnwuIJ8OsDAnfsHI9OsrSb9b6zjCV7JNWtkpWje7iLnAasx0XyJirkpSTqKYKXD3dMsGwYEypXklO2qScJ8MpbduxF/j7L42UeIzh5Y0QLICi/ZQ3v17L+NYovqt/9E2lMqRx2fW4ezAhsUK/g2udqOazvGBbDT/XBz8FxmMXhZTua00+39n0EZy8po3u4i5wGrMdF8iYq5KUk6eIV8F3edzckdD+ah57BNJ4BZEFTFq3xLOVS9WI+1YAbcV7Az/SLJI5Gw/XNR/sdyJlwM3kr8T2LX1T/DLrHmfeepaiF9RNAt7ZX0iKnmING+Ub6QUrOFwYQ5NVX9uBu1tBMyTI6XXm1V1mL4k1MWIg==]]></req_info></xml>";
    Map<String, String> xmlMap = WxUtils.readStringXmlOut(refundXml);
    String reqInfo = xmlMap.get("req_info");
    //    String resultXml = RefundUtil.decryptData(req_info, DataConstants.SECRET);
    //    Map<String, String> reqInfoMap = WxUtils.readStringXmlOut(resultXml);
    //    System.out.println(req_info);
    //    System.out.println("-----------------");
    //    System.out.println(reqInfoMap);
    //    String reqInfo =
    //
    // "AUzul+8g702ol8wjx0KTj3iol2EUZ/FUlapMKPeXxlrvo4X0tMWkEvmcpHEWEsw46A1zpEJHfL0xA5id/3yRBmb3g9S9jukwHhveIMFx0tnZmWyKfCjXYPqc8EfCBVmlQ7pvoQJzqAtNCWIoAa20/4VfMM6Yrf6iN440x6CO5RLMvfcVCJW8DcBVW4kHji4iDtV9ez4D6lULANbvvschifgloNC0ZCGHly4qU0Tl1ma5NLeKCowAJki+qURDiyYCzkA23aJGosmo0G/tDVHPjXM7q3CAz2bz/FopeNfbUcfo9IY8qfZfMHR+gvbf6N8da7qapgQBtYUyZ3L7oVG1uSVymjffJ8TE5REao7v8ww92dwp2ZLv+BnDHSBc5yYqhGxW8ZamWrryj5lD5VPwo6On+NZSAFCjprIDCGsu4I+1TXodFxt0R0LltZ4FZoQOYCjqHRG5pT9QOvl2CKwTRhS3buD9tzRryWmMPgSahD9aJQUvJpWqvQffCk3TNpYWVcSj86Kzhgmx+DE/tNWkm/RErWRdB4/k8x79fnOP9uJz06iim2vzJrSZHKDxv2hmIdpG2RuCah/boyJaFzJhBzWmbzVKEc4BNwpnvtOwkalUTQ7+mNVhMQkgBbSGGHCwbSWMH/Ogh+Kbno+XuLqg97izuyw+hgxeaQ6ZHEb3rd32SNr/CXxnwuIJ8OsDAnfsHWiyB6Wk2ekZI0bXYftYb2hbeVLrYDa1wsL/7/JFv9Gc1gLP49LyjiH2B4NlCSRwyScJ8MpbduxF/j7L42UeIzj2pvS5xGCgnflQCwFaJ+ad49hj1LzBgq4wPJr1UhiR9aLyMre9NJ9IsTkx0F3c0Alp6ab0aFt+Gp+nozPF9hMq4xLs1WuQCaLqe/zIofjhNqM4xV9tbs4+wiSIdMXvgOt5VlEgcD7xVeFIdkkeLqN6JiXgY9hv7IWonpqwsCeRe3iR6PpcCPzo+f1ZlB/7nA61pS6VjCyc4HUM8ZiPCpZZ/5qUmrjdNZUYj1uLty6tD86kXd4KddZ4ThuCL7+KPRcQWcpXKHMFskJ/HPX6OGLIzHM5w0CxlFtVkWDDmMC8L";

    String result = RefundAESutil.decryptData(reqInfo);
    System.out.println(result);

    //    byte[] reqInfoB = Base64.decodeBase64(reqInfo);
    //    String key_ = DigestUtils.md5Hex(DataConstants.SECRET).toLowerCase();
    //
    //    if (Security.getProvider("BC") == null) {
    //      Security.addProvider(new BouncyCastleProvider());
    //    }
    //    Cipher cipher = Cipher.getInstance("PKCS7Padding");
    //    SecretKeySpec secretKeySpec = new SecretKeySpec(key_.getBytes(), "AES");
    //    cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
    //    System.out.println(new String(cipher.doFinal(reqInfoB)));
  }
}
