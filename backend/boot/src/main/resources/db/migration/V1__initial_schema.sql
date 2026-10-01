CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE auth_user (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    CONSTRAINT uk_auth_user_username UNIQUE (username),
    CONSTRAINT uk_auth_user_email UNIQUE (email)
);

CREATE TABLE refresh_token (
    token_hash VARCHAR(255) PRIMARY KEY,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    session_max_until TIMESTAMP WITH TIME ZONE NOT NULL,
    revoked BOOLEAN NOT NULL,
    user_id UUID NOT NULL,

    CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id) REFERENCES auth_user(id)
);

CREATE TABLE routing_edge (
    id UUID PRIMARY KEY,
    provider VARCHAR(50) NOT NULL,
    external_id VARCHAR(255) NOT NULL,

    CONSTRAINT uk_routing_edge_provider_external_id UNIQUE (provider, external_id)
);

CREATE TABLE street_segment (
    id UUID PRIMARY KEY,
    routing_edge_id UUID NOT NULL,
    geometry geometry(LineString, 4326) NOT NULL,

    CONSTRAINT fk_street_segment_routing_edge FOREIGN KEY (routing_edge_id) REFERENCES routing_edge(id)
);

CREATE INDEX idx_street_segment_geography
    ON street_segment
    USING GIST ((geometry::geography));

CREATE TABLE game_map (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE game_map_street_segment (
    game_map_id UUID NOT NULL,
    street_segment_id UUID NOT NULL,
    state VARCHAR(50) NOT NULL,

    CONSTRAINT pk_game_map_street_segment PRIMARY KEY (game_map_id, street_segment_id),
    CONSTRAINT fk_game_map_street_segment_game_map FOREIGN KEY (game_map_id) REFERENCES game_map(id),
    CONSTRAINT fk_game_map_street_segment_street_segment FOREIGN KEY (street_segment_id) REFERENCES street_segment(id)
);

CREATE TABLE game (
    id UUID PRIMARY KEY,
    game_map_id UUID NOT NULL,
    state VARCHAR(50) NOT NULL,
    started_at TIMESTAMP WITH TIME ZONE,
    finished_at TIMESTAMP WITH TIME ZONE,

    CONSTRAINT fk_game_game_map FOREIGN KEY (game_map_id) REFERENCES game_map(id)
);

CREATE TABLE player (
    user_id UUID NOT NULL,
    game_id UUID NOT NULL,
    role VARCHAR(50) NOT NULL,

    CONSTRAINT pk_player PRIMARY KEY (user_id, game_id),
    CONSTRAINT fk_player_user FOREIGN KEY (user_id) REFERENCES auth_user(id),
    CONSTRAINT fk_player_game FOREIGN KEY (game_id) REFERENCES game(id)
);
