package pt.joja.mapper;

import org.apache.ibatis.annotations.*;
import pt.joja.entities.Department;

//@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id = #{id}")
    Department getDepartmentById(Integer id);

    @Delete("delete from department where id = #{id}")
    Integer deleteDepartmentById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into department (department_name) values(#{departmentName})")
    Integer insertDepartment(Department department);

    @Update("update department set department_name=#{departmentName} where id=#{id}")
    int updateDepartment(Department department);

}
