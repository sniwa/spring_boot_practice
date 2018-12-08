package com.example.introduction.gen.mapper;

import com.example.introduction.gen.entity.TaskPermissions;
import com.example.introduction.gen.entity.TaskPermissionsExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskPermissionsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_permissions
     *
     * @mbg.generated
     */
    long countByExample(TaskPermissionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_permissions
     *
     * @mbg.generated
     */
    int deleteByExample(TaskPermissionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_permissions
     *
     * @mbg.generated
     */
    int insert(TaskPermissions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_permissions
     *
     * @mbg.generated
     */
    int insertSelective(TaskPermissions record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_permissions
     *
     * @mbg.generated
     */
    List<TaskPermissions> selectByExample(TaskPermissionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_permissions
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TaskPermissions record, @Param("example") TaskPermissionsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_permissions
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TaskPermissions record, @Param("example") TaskPermissionsExample example);
}