package com.mhp.coding.challenges.mapping;

import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.blocks.ArticleBlock;
import com.mhp.coding.challenges.mapping.models.db.blocks.TextBlock;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import com.mhp.coding.challenges.mapping.services.ArticleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.*;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest extends ArticleRepository {

    @Mock
    ArticleRepository repository;

    @InjectMocks
    private ArticleService articleService;


    private Article article1;
    private Article article2;

    @Before
    public void setUp() {

        //Create sample data for first test article
        long id = 1;
        article1=new Article();
        article1.setId(id);
        article1.setAuthor("Max Mustermann");
        article1.setDescription("Article Description " + id);
        article1.setTitle("Article Nr.: " + id);
        article1.setLastModifiedBy("Hans Müller");
        article1.setLastModified(new Date());

        Set<ArticleBlock> blockCollection = new HashSet<>();
        TextBlock textBlock = new TextBlock();
        textBlock.setText("Third Block Text for " + id);
        textBlock.setSortIndex(3);
        blockCollection.add(textBlock);

        textBlock = new TextBlock();
        textBlock.setText("First Block Text for " + id);
        textBlock.setSortIndex(1);
        blockCollection.add(textBlock);

        textBlock = new TextBlock();
        textBlock.setText("Second Block Text for " + id);
        textBlock.setSortIndex(2);
        blockCollection.add(textBlock);

        article1.setBlocks(blockCollection);

        //Create sample data for second test article
        id = 2;
        article2=new Article();
        article2.setId(id);
        article2.setAuthor("Smith Bill");
        article2.setDescription("Article Description " + id);
        article2.setTitle("Article Nr.: " + id);
        article2.setLastModifiedBy("Elizabeth");
        article2.setLastModified(new Date());
        article2.setBlocks(blockCollection);

    }

    @Test
    public void GetAllArticles() {

        //Intercept repo with test articles
        given(repository.all()).willReturn(Arrays.asList(article1,article2));

        //call article service for article list
        List<ArticleDto> result = articleService.list();

        //assert respond has 2 objects
        assertThat(result).hasSize(2);

        //assert first article has Id=1
        assertEquals(Long.valueOf(1L),result.get(0).getId());

        //verify that repository "all" method was called once
        verify(repository, times(1)).all();
    }


    @Test
    public void SearchArticleById() {

        //Intercept repo with test articles
        given(repository.findBy(Long.valueOf(1L))).willReturn(article1);

        //call article service for article record
        ArticleDto result = articleService.articleForId(Long.valueOf(1L));

        //assert article has Id=1
        assertEquals(Long.valueOf(1L),result.getId());

        //verify that repository "findBy" method was called once
        verify(repository, times(1)).findBy(Long.valueOf(1L));
    }


}
