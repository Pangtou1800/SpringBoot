package pt.joja.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pt.joja.bean.Department;

@Mapper
public interface DepartmentMapper {
    @Select("select * from department where id = #{id}")
    Department getDepartmentById(Integer id);
}
