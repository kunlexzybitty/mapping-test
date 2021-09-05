package com.mhp.coding.challenges.mapping.mappers;

import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.db.blocks.ArticleBlock;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.models.dto.blocks.ArticleBlockDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArticleMapper {

    public ArticleDto map(Article article)
    {
        //Return empty object if Id is null
        if(article.getId() == null) return new ArticleDto();

        ArticleDto _articleDto = new ArticleDto();
        _articleDto.setAuthor(article.getAuthor());
        _articleDto.setDescription(article.getDescription());
        _articleDto.setId(article.getId());
        _articleDto.setTitle(article.getTitle());

        //Block Collection
        Collection<ArticleBlockDto> _blockCollection =  new LinkedHashSet<ArticleBlockDto>();

        //Block Sort comparator
        Comparator<ArticleBlock> compareByIndex = (ArticleBlock a1, ArticleBlock a2) -> new Integer(a1.getSortIndex()).compareTo( new Integer(a2.getSortIndex()) );

        //Sort block collection and collect as List
        List<ArticleBlock> _articleBlockList = article.getBlocks()
                                               .stream()
                                               .sorted(compareByIndex)
                                               .collect(Collectors.toList());

        //Create ArticleBlockDto objects from sorted article blocks
        for (ArticleBlock iterator : _articleBlockList) {

            ArticleBlockDto newArticleBlockDto = new ArticleBlockDto();
            newArticleBlockDto.setSortIndex(iterator.getSortIndex());
            _blockCollection.add( newArticleBlockDto );

        }

        _articleDto.setBlocks(_blockCollection);

        return _articleDto;
    }

    public Article map(ArticleDto articleDto) {
        // Nicht Teil dieser Challenge.
        return new Article();
    }
}
