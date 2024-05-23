package com.Aswat.services.jwt;

import com.Aswat.Dtos.CategoryDto;
import com.Aswat.Dtos.TypeDto;
import com.Aswat.entity.Category;
import com.Aswat.entity.Type;
import com.Aswat.reposistories.CategoryRepo;
import com.Aswat.reposistories.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TypeServiceImpl implements  TypeService{

    private final TypeRepository typeRepository;

    public TypeServiceImpl(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }


    public Type createType(TypeDto typeDto) {
        Type type= new Type();
        type.setType(typeDto.getType());
        return typeRepository.save(type);

    }

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }
}
