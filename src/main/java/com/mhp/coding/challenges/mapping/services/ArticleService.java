package com.mhp.coding.challenges.mapping.services;

import com.mhp.coding.challenges.mapping.mappers.ArticleMapper;
import com.mhp.coding.challenges.mapping.models.db.Article;
import com.mhp.coding.challenges.mapping.models.dto.ArticleDto;
import com.mhp.coding.challenges.mapping.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    private final ArticleMapper mapper;

    @Autowired
    public ArticleService(ArticleRepository repository, ArticleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ArticleDto> list()
    {
        final List<Article> articles = repository.all();

        //Arraylist to hold ArticleDto objects
        ArrayList<ArticleDto> _articleList = new ArrayList<ArticleDto>();

        //Convert articles to articleDto using Article Mapper
        for(Article _article : articles)
        {
            _articleList.add(new ArticleMapper().map(_article));
        }

        return _articleList;
    }

    public ArticleDto articleForId(Long id) {

        final Article _matchedArticle = repository.findBy(id);

        //if article was found
        if( _matchedArticle.getId() != null ) return new ArticleMapper().map(_matchedArticle);
        else
        {
            //If not found, throw 404 error
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Article not found"
            );
        }
    }

    public ArticleDto create(ArticleDto articleDto) {
        final Article create = mapper.map(articleDto);
        repository.create(create);
        return mapper.map(create);
    }
}
