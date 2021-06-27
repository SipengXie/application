package thriftServer.javaRpcToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;
/*
 RPC Token类 用于验证调用RPC的服务身份
 */
public class javaRpcToken {

    private static final Logger log = LoggerFactory.getLogger(javaRpcToken.class);
// secret关键词可以改变
    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("VictorXie");
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }

    public static String createJavaRpcToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    public static Map<String, Object> parserJavaRpcToken(String jwt) {
        try {
            return Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
        } catch (Exception e) {
            log.error("json Rpc token verify failed");
            return null;
        }
    }
}
