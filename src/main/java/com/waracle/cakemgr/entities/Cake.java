package com.waracle.cakemgr.entities;

import java.net.URI;
import java.util.UUID;

/**
 * Models the Cake entity, containing the following attributes :-
 *
 * - id - the UUID of the Cake
 * - title - the title pf the Cake.
 * - description - the description of the Cake.
 * - imageURL - the URI of the Cake image.
 */
public record Cake(UUID id, String title, String description, URI imageURL) {
}
