package com.waracle.cakemgr;

import static org.junit.Assert.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CakeStoreTest {

    private CakeStore testee;
    @Mock
    private Criteria mockCriteria;
    @Mock
    private Transaction mockTransaction;
    @Mock
    private Session mockSession;
    @Captor
    private ArgumentCaptor<Cake> cakeArgument;
    @Mock
    private CakeFactory mockCakeFactory;

    @Before
    public void setUp() {
        testee = new CakeStore();
        when(mockSession.createCriteria(Cake.class)).thenReturn(mockCriteria);
        when(mockCriteria.add(any(Criterion.class))).thenReturn(mockCriteria);
        when(mockSession.getTransaction()).thenReturn(mockTransaction);
    }

    @Test
    public void cakeIsStoredCorrectlyWhenNotDuplicate() {
        Cake cake = new Cake();
        when(mockCriteria.list()).thenReturn(new ArrayList());
        testee.store(cake,  mockSession);
        verify(mockTransaction).commit();
        verify(mockSession).close();
    }

    @Test
    public void cakeDescriptionIsUpdatedWhenDuplicate() {
        Cake cake1 = new Cake();
        cake1.setTitle("Walnut Cake");
        when(mockCriteria.list()).thenReturn(Arrays.asList(cake1));
        Cake cake2 = new Cake();
        cake2.setTitle("Walnut Cake");
        cake2.setDescription("updated description");
        testee.store(cake2,  mockSession);
        verify(mockTransaction).commit();
        verify(mockSession).close();
        verify(mockSession).persist(cakeArgument.capture());
        Cake cake3 = cakeArgument.getValue();
        assertThat(cake3.getDescription(), is("updated description"));
    }
}
