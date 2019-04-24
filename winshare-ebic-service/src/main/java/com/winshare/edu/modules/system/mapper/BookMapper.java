package com.winshare.edu.modules.system.mapper;

import com.winshare.edu.modules.system.entity.BookInfo;
import tk.mybatis.mapper.common.Marker;

import java.util.List;

public interface BookMapper extends Marker {

    List<BookInfo> findList(BookInfo tv);

    List<BookInfo> findBookList(BookInfo tv);

    int add(BookInfo tv);

    int update(BookInfo tv);

    BookInfo findById(BookInfo id);

}
