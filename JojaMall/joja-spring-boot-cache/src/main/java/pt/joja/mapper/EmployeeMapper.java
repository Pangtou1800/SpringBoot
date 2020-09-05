package pt.joja.mapper;

import org.apache.ibatis.annotations.*;
import pt.joja.bean.Employee;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    Employee getEmployeeById(Integer id);

    @Update("update employee set lastName=#{lastName}, email=#{email}, gender=#{gender}, d_id=#{dId}")
    void updateEmployee(Employee employee);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into employee (lastName, email, gender, d_id) values(#{lastName}, #{email}, #{gender}, #{dId}")
    void insertEmployee(Employee employee);

    @Delete("delete from employee where id=#{id}")
    void deleteEmployeeById(Integer id);

}
