package com.security.services.config.oauth;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


/** Responsible to get access_token and delete if already exists */
@Component
public class CustomJdbcTokenStore extends JdbcTokenStore {

    private static final Logger LOG = Logger.getLogger(CustomJdbcTokenStore.class);

    private static final String DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT =
            "select token_id, token from oauth_access_token where user_name = ?";

    private AuthenticationKeyGenerator authenticationKeyGenerator = new CustomAuthenticationKeyGenerator();

    private final JdbcTemplate jdbcTemplate;

    public CustomJdbcTokenStore(DataSource dataSource) {
        super(dataSource);
        Assert.notNull(dataSource, "DataSource required");
        setAuthenticationKeyGenerator(authenticationKeyGenerator);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Responsible to get access token and remove previous access_token and refresh_token if exists
     *
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        OAuth2AccessToken accessToken = null;

        LOG.info(" Getting access token ");
        try {
            accessToken = jdbcTemplate
                    .queryForObject(DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT,
                            new RowMapper<OAuth2AccessToken>() {
                                public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                                    return deserializeAccessToken(rs.getBytes(2));
                                }
                            }, ((org.springframework.security.core.userdetails.User) authentication.getPrincipal())
                                    .getUsername());
        }
        catch (EmptyResultDataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed to find access token for authentication " + authentication);
            }
        }
        catch (IllegalArgumentException e) {
            LOG.error("Could not extract access token for authentication " + authentication, e);
        }

        if (accessToken != null) {
            removeRefreshToken(accessToken.getRefreshToken());
            removeAccessToken(
                    ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername());
            accessToken = null;
        }
        return accessToken;
    }

    /**
     * Remove access_token by username
     *
     * @param username
     */
    @Override
    public void removeAccessToken(String username) {
        jdbcTemplate.update("delete from oauth_access_token where user_name = ?", username);
    }

    /**
     * Responsible to get user's OAuth2AccessToken
     *
     * @param username user to get
     * @return OAuth2AccessToken if exist
     */
    public OAuth2AccessToken findByUsername(String username) {
        LOG.info(" Finding access token  by username " + username);
        try {
            return jdbcTemplate
                    .queryForObject(DEFAULT_ACCESS_TOKEN_FROM_AUTHENTICATION_SELECT_STATEMENT,
                            new RowMapper<OAuth2AccessToken>() {
                                public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                                    return deserializeAccessToken(rs.getBytes(2));
                                }
                            }, username);
        }
        catch (EmptyResultDataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Failed to find access token for authentication " + username);
            }
        }
        catch (IllegalArgumentException e) {
            LOG.error("Could not extract access token for authentication " + username, e);
        }
        return null;
    }
}
