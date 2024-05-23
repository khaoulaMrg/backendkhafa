package com.Aswat.reposistories;

import com.Aswat.entity.Category;
import com.Aswat.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByType(String typeName);

}
