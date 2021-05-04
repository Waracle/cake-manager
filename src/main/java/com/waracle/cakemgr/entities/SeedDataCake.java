package com.waracle.cakemgr.entities;

import java.net.URI;

/**
 * Models the Cake seed data entities, containing the following attributes :-
 *
 * - title - the title pf the Cake.
 * - description - the description of the Cake.
 * - imageURL - the URI of the Cake image.
 */
public record SeedDataCake(String title, String description, URI imageURL) {
}
