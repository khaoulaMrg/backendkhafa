package com.Aswat.services.jwt;

import com.Aswat.Dtos.CategoryDto;
import com.Aswat.Dtos.TypeDto;
import com.Aswat.entity.Category;
import com.Aswat.entity.Type;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeService {

    Type createType(TypeDto typeDto);

    List<Type> getAllTypes();
}
