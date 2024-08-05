//package com.example.quizz;
//
//public class SercurityUtil {
//    private final JwtEncoder jwtEncoder;
//
//    public SercuryUtil(JwtEncoder jwtEncoder) {
//        this.jwtEncoder = jwtEncoder;
//    }
//
//    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;
//
//    @Value("${hct_14.jwt.base64-secret}")
//    private String jwtKey;
//    @Value("${hct_14.jwt.accsess-token-validity-in-seconds}")
//    private long JwtExpirationAccsessToken;
//    @Value("${hct_14.jwt.refresh-token-validity-in-seconds}")
//    private long refreshTokenExpiration;
//
//    public String createAccessToken(String email, ResLoginDTO dto) {
//        ResLoginDTO.UserInsideToken userToken = new ResLoginDTO.UserInsideToken();
//        userToken.setId(dto.getUser().getId());
//        userToken.setEmail(dto.getUser().getEmail());
//        userToken.setName(dto.getUser().getName());
//
//        Instant now = Instant.now();
//        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);
//
//        // hardcode permission (for testing)
//        List<String> listAuthority = new ArrayList<String>();
//
//        listAuthority.add("ROLE_USER_CREATE");
//        listAuthority.add("ROLE_USER_UPDATE");
//
//        // @formatter:off
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuedAt(now)
//                .expiresAt(validity)
//                .subject(email)
//                .claim("user", userToken)
//                .claim("permission", listAuthority)
//                .build();
//
//        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
//        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
//
//    }
//
//    public String createRefreshToken(String email, ResLoginDTO dto) {
//        Instant now = Instant.now();
//        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);
//
//        ResLoginDTO.UserInsideToken userToken = new ResLoginDTO.UserInsideToken();
//        userToken.setId(dto.getUser().getId());
//        userToken.setEmail(dto.getUser().getEmail());
//        userToken.setName(dto.getUser().getName());
//
//        // @formatter:off
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuedAt(now)
//                .expiresAt(validity)
//                .subject(email)
//                .claim("user", userToken)
//                .build();
//
//        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
//        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
//
//    }
//
//    private SecretKey getSecretKey() {
//        byte[] keyBytes = Base64.from(jwtKey).decode();
//        return new SecretKeySpec(keyBytes, 0, keyBytes.length,
//                JWT_ALGORITHM.getName());
//    }
//
//    public Jwt checkValidRefreshToken(String token){
//        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
//                getSecretKey()).macAlgorithm(SercuryUtil.JWT_ALGORITHM).build();
//        try {
//            return jwtDecoder.decode(token);
//        } catch (Exception e) {
//            System.out.println(">>> Refresh Token error: " + e.getMessage());
//            throw e;
//        }
//    }
//
//
//    public static Optional<String> getCurrentUserLogin() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
//    }
//
//    private static String extractPrincipal(Authentication authentication) {
//        if (authentication == null) {
//            return null;
//        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
//            return springSecurityUser.getUsername();
//        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
//            return jwt.getSubject();
//        } else if (authentication.getPrincipal() instanceof String s) {
//            return s;
//        }
//        return null;
//    }
//
//    public static Optional<String> getCurrentUserJWT() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        return Optional.ofNullable(securityContext.getAuthentication())
//                .filter(authentication -> authentication.getCredentials() instanceof String)
//                .map(authentication -> (String) authentication.getCredentials());
//    }
//
//
//}
