package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dao.CakeEntity;
import com.waracle.cakemgr.dao.CakeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CakeServiceTest {

  @Mock
  private CakeRepository repository;

  @InjectMocks
  private CakeService cakeService;

  @Test
  public void testPopulateCakesIsCalled() throws IOException {
    ReflectionTestUtils.setField(cakeService, "cakeUrl",
        "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json");

    cakeService.populateCakes();

    verify(repository).saveAll(any(Set.class));
  }

  @Test
  public void testGetCakesReturnsListOfEntitiesFromRepository() {
    CakeEntity cakeEntity1 = new CakeEntity();
    cakeEntity1.setTitle("entity1Title123");
    cakeEntity1.setDesc("entity1Description123");
    cakeEntity1.setImage("entity1ImageUrl123");

    CakeEntity cakeEntity2 = new CakeEntity();
    cakeEntity2.setTitle("entity2Title123");
    cakeEntity2.setDesc("entity2Description123");
    cakeEntity2.setImage("entity2ImageUrl123");

    when(repository.findAll()).thenReturn(List.of(cakeEntity1, cakeEntity2));

    List<CakeEntity> cakeEntityEntities = cakeService.getCakes();

    assertThat(cakeEntityEntities.size()).isEqualTo(2);
    assertThat(cakeEntityEntities.get(0)).isEqualTo(cakeEntity1);
    assertThat(cakeEntityEntities.get(1)).isEqualTo(cakeEntity2);
  }

  @Test
  public void testWriteCakeSavesToRepository() {
    CakeEntity cakeEntity = new CakeEntity();
    cakeEntity.setTitle("entityTitle123");
    cakeEntity.setDesc("entityDescription123");
    cakeEntity.setImage("entityImageUrl123");

    cakeService.writeCake(cakeEntity);

    verify(repository, times(1)).save(cakeEntity);
  }

}
