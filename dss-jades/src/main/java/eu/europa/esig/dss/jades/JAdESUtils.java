package eu.europa.esig.dss.jades;

import static eu.europa.esig.dss.jades.JAdESHeaderParameterNames.ADO_TST;
import static eu.europa.esig.dss.jades.JAdESHeaderParameterNames.SIG_D;
import static eu.europa.esig.dss.jades.JAdESHeaderParameterNames.SIG_PID;
import static eu.europa.esig.dss.jades.JAdESHeaderParameterNames.SIG_PL;
import static eu.europa.esig.dss.jades.JAdESHeaderParameterNames.SIG_T;
import static eu.europa.esig.dss.jades.JAdESHeaderParameterNames.SR_ATS;
import static eu.europa.esig.dss.jades.JAdESHeaderParameterNames.SR_CM;
import static eu.europa.esig.dss.jades.JAdESHeaderParameterNames.X5T_O;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jose4j.base64url.Base64Url;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.jose4j.jwx.CompactSerializer;
import org.jose4j.jwx.HeaderParameterNames;
import org.jose4j.lang.StringUtil;

import eu.europa.esig.dss.enumerations.DigestAlgorithm;
import eu.europa.esig.dss.model.DSSException;
import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.validation.timestamp.TimestampToken;

public class JAdESUtils {
	
	public static final String MIME_TYPE_APPLICATION_PREFIX = "application/";
	
	/**
	 * Contains header names that are supported to be present in the critical attribute
	 */
	private static final Set<String> criticalHeaders;
	
	static {
		// JAdES EN 119-812 constraints
		criticalHeaders = Stream.of(SIG_T, X5T_O, SR_CM, SIG_PL, SR_ATS, ADO_TST, SIG_PID, SIG_D)
				.collect(Collectors.toSet());
		criticalHeaders.add(HeaderParameterNames.BASE64URL_ENCODE_PAYLOAD); // b64 #RFC7797
	}
	
	/**
	 * Returns ASCII-encoded array
	 * 
	 * @param str {@link String} to encode to ASCII
	 * @return byte array
	 */
	public static byte[] getAsciiBytes(String str) {
		return StringUtil.getBytesAscii(str);
	}
	
	/**
	 * Returns a base64Url encoded string
	 * 
	 * @param binary a byte array to encode
	 * @return base64Url encoded {@link String}
	 */
	public static String toBase64Url(byte[] binary) {
		return Base64Url.encode(binary);
	}
	
	/**
	 * Concatenates the given strings with a '.' (dot) between.
	 * Example: "xxx", "yyy", "zzz" -> "xxx.yyy.zzz"
	 * 
	 * @param strings a list of {@link String}s to concatenate
	 * @return a concatenation string result
	 */
	public static String concatenate(String... strings) {
		return CompactSerializer.serialize(strings);
	}
	
	/**
	 * Returns array of supported critical headers
	 * 
	 * @return array of supported critical header strings
	 */	
	public static String[] getSupportedCriticalHeaders() {
		return criticalHeaders.toArray(new String[criticalHeaders.size()]);
	}
	
	/**
	 * Creates a 'DigAlgVal' JSONObject from the given values
	 * 
	 * @param digestValue a byte array representing a hash value
	 * @param digestAlgorithm {@link DigestAlgorithm} has been used to generate the value
	 * @return 'DigAlgVal' {@link JSONObject}
	 */
	public static JSONObject getDigAndValObject(byte[] digestValue, DigestAlgorithm digestAlgorithm) {
		Objects.requireNonNull(digestValue, "digestValue must be defined!");
		Objects.requireNonNull(digestAlgorithm, "digestAlgorithm must be defined!");
		
		Map<String, Object> digAlgValParams = new HashMap<>();
		digAlgValParams.put(JAdESHeaderParameterNames.DIG_ALG, digestAlgorithm.getUri());
		digAlgValParams.put(JAdESHeaderParameterNames.DIG_VAL, Utils.toBase64(digestValue));
		
		JSONObject digAlgValParamsObject = new JSONObject(digAlgValParams);
		
		Map<String, Object> digAldVal = new HashMap<>();
		digAldVal.put(JAdESHeaderParameterNames.DIG_ALG_VAL, digAlgValParamsObject);
		
		return new JSONObject(digAldVal);
	}

	/**
	 * Creates an 'oid' JSONObject according to EN 119-182 ch. 5.4.1 The oId data type
	 * 
	 * @param uri {@link String} URI defining the object.
	 * @return 'oid' {@link JSONObject}
	 */
	public static JSONObject getOidObject(String uri) {
		return getOidObject(uri, null, null);
	}

	/**
	 * Creates an 'oid' JSONObject according to EN 119-182 ch. 5.4.1 The oId data type
	 * 
	 * @param uri {@link String} URI defining the object. The property is REQUIRED.
	 * @param desc {@link String} the object description. The property is OPTIONAL.
	 * @param docRefs a list of {@link String} URIs containing any other additional information about the object. 
	 * 				The property is OPTIONAL.
	 * @return 'oid' {@link JSONObject}
	 */
	public static JSONObject getOidObject(String uri, String desc, List<String> docRefs) {
		Objects.requireNonNull(uri, "uri must be defined!");
		
		Map<String, Object> oidParams = new HashMap<>();
		oidParams.put(JAdESHeaderParameterNames.ID, uri);
		if (Utils.isStringNotEmpty(desc)) {
			oidParams.put(JAdESHeaderParameterNames.DESC, desc);
		}
		if (Utils.isCollectionNotEmpty(docRefs)) {
			oidParams.put(JAdESHeaderParameterNames.DOC_REFS, new JSONArray(docRefs));
		}
		
		return new JSONObject(oidParams);
	}
	
	/**
	 * Creates a 'tstContainer' JSONObject according to EN 119-182 ch. 5.4.3.3 The tstContainer type
	 * 
	 * @param timestampTokens a list of {@link TimestampToken}s to incorporate
	 * @param canonicalizationMethodUri a canonicalization method (OPTIONAL, e.g. shall not be present for content timestamps)
	 * @return 'tstContainer' {@link JSONObject}
	 */
	public static JSONObject getTstContainer(List<TimestampToken> timestampTokens, String canonicalizationMethodUri) {
		if (Utils.isCollectionEmpty(timestampTokens)) {
			throw new DSSException("Impossible to create 'tstContainer'. TimestampTokens cannot be null or empty!");
		}

		Map<String, Object> tstContainerParams = new HashMap<>();
		if (canonicalizationMethodUri != null) {
			tstContainerParams.put(JAdESHeaderParameterNames.CANON_ALG, canonicalizationMethodUri);
		}
		List<JSONObject> tsTokens = new ArrayList<>();
		for (TimestampToken timestampToken : timestampTokens) {
			JSONObject tstToken = getTstToken(timestampToken);
			tsTokens.add(tstToken);
		}
		JSONArray tsTokensArray = new JSONArray(tsTokens);
		tstContainerParams.put(JAdESHeaderParameterNames.TST_TOKENS, tsTokensArray);
		
		return new JSONObject(tstContainerParams);
	}
	
	/**
	 * Creates a 'tstToken' JSONObject according to EN 119-182 ch. 5.4.3.3 The tstContainer type
	 * 
	 * @param timestampToken {@link TimestampToken}s to incorporate
	 * @return 'tstToken' {@link JSONObject}
	 */
	private static JSONObject getTstToken(TimestampToken timestampToken) {
		Objects.requireNonNull(timestampToken, "timestampToken cannot be null!");
		
		Map<String, Object> tstTokenParams = new HashMap<>();
		// only RFC 3161 TimestampTokens are supported
		// 'type', 'encoding' and 'specRef' params are not need to be defined (see EN 119-182 ch. 5.4.3.3)
		tstTokenParams.put(JAdESHeaderParameterNames.VAL, Utils.toBase64(timestampToken.getEncoded())); // DER-encoded value
		
		return new JSONObject(tstTokenParams);
	}

}
