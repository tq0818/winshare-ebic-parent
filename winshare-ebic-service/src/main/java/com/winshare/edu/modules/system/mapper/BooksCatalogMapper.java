package com.winshare.edu.modules.system.mapper;

import com.winshare.edu.modules.system.entity.BooksCatalogInfo;
import tk.mybatis.mapper.common.Marker;

import java.util.List;

public interface BooksCatalogMapper extends Marker {

    List<BooksCatalogInfo> findByBookId(Integer id);

    List<BooksCatalogInfo> findKnowledge(Integer id);

    List<BooksCatalogInfo> findByBook(Integer id);
}
