package com.project.webstore.services;

import com.project.webstore.domains.Categories;
import com.project.webstore.repositories.CategoriesRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService {
    
    @Autowired
    private CategoriesRepository catrepo;
    
    public Categories buscar(Integer id) {        
        Optional<Categories> obj = catrepo.findById(id);
        return obj.orElse(null);
    }
    
}