package com.waracle.cakemgr.service;

import com.waracle.cakemgr.dao.Cake;
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
    Cake cake1 = new Cake();
    cake1.setTitle("Cake Title");
    cake1.setDesc("Cake desc");
    cake1.setImage("Cake Img. Url");

    Cake cake2 = new Cake();
    cake2.setTitle("Cake Title 123");
    cake2.setDesc("Cake desc 123");
    cake2.setImage("Cake Img. Url 123");

    when(repository.findAll()).thenReturn(List.of(cake1, cake2));

    List<Cake> cakeEntities = cakeService.getCakes();

    assertThat(cakeEntities.size()).isEqualTo(2);
    assertThat(cakeEntities.get(0)).isEqualTo(cake1);
    assertThat(cakeEntities.get(1)).isEqualTo(cake2);
  }

  @Test
  public void testWriteCakeSavesToRepository() {
    Cake cake = new Cake();
    cake.setTitle("Cake Title 9090");
    cake.setDesc("Cake desc 9090");
    cake.setImage("Cake Img Url 9090");

    cakeService.writeCake(cake);

    verify(repository, times(1)).save(cake);
  }

}
