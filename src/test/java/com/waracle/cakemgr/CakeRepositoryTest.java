package com.waracle.cakemgr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class CakeRepositoryTest {

    @Autowired
    private CakeRepository cakeRepository;


    @Test
    public void shouldSaveCake() {
        final String title = "test";
        final Cake cake = cakeRepository.save(Cake.builder().title(title).desc("desc").image("http://test.png").build());

        assertThat(cake.getTitle()).contains(title);
        assertThat(cake.getId()).isGreaterThan(0);
    }


    @Test
    public void shouldNotSaveCakeWithNoTile() {
        final Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            cakeRepository.save(Cake.builder().build());
        });

        assertThat(exception.getMessage()).contains("not-null");
    }

    @Test
    public void shouldNotSaveDuplicateCake() {
        final String title = "test";
        final Cake cake = cakeRepository.save(Cake.builder().title(title).desc("desc").image("http://test.png").build());

        assertThat(cake.getTitle()).contains(title);
        assertThat(cake.getId()).isGreaterThan(0);

        final Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            cakeRepository.save(cake.toBuilder().id(null).build());
        });

        assertThat(exception.getMessage()).contains("constraint");
    }

}