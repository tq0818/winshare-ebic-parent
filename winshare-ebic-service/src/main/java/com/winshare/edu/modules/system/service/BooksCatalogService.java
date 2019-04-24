package com.winshare.edu.modules.system.service;


import com.winshare.edu.modules.system.entity.BooksCatalogInfo;
import com.winshare.edu.modules.system.mapper.BooksCatalogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("booksCatalogService")
public class BooksCatalogService {
    @Autowired
    private BooksCatalogMapper booksCatalogMapper;

    public List<BooksCatalogInfo> findByBookId(Integer id){

        return booksCatalogMapper.findByBookId(id);
    }

    public List<BooksCatalogInfo> findKnowledge(Integer id){

        return booksCatalogMapper.findKnowledge(id);
    }

    public List<BooksCatalogInfo> findByBook(Integer id){

        return booksCatalogMapper.findByBook(id);
    }

}
