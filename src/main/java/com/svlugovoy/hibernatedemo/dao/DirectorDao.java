package com.svlugovoy.hibernatedemo.dao;

import com.svlugovoy.hibernatedemo.domain.Director;

public interface DirectorDao {

    Director findById(Long id);

}
