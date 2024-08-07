package com.example.quizz.Service;

import com.example.quizz.Dto.ResRoleDTO.ResCreateRole;
import com.example.quizz.Dto.ResRoleDTO.ResRoleDTO;
import com.example.quizz.Dto.ResUserDTO;
import com.example.quizz.Dto.ResultPaginationDTO;
import com.example.quizz.Entity.Role;
import com.example.quizz.Entity.User;
import com.example.quizz.Exception.IdvalidException;
import com.example.quizz.Repository.RoleRepository;
import com.example.quizz.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserService userRepository;

    public RoleService(RoleRepository roleRepository, UserService userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    public Optional<Role> findByid(int id){
        return this.roleRepository.findById(id);
    }
    public boolean findByName(String name){
        return this.roleRepository.existsByName(name);
    }
    public Role createRole(Role role) throws IdvalidException {
        boolean roleCheck = this.roleRepository.existsByName(role.getName());
        if (roleCheck==false){
            return  this.roleRepository.save(role);

        }
        throw new IdvalidException("role nay da ton tai");
    }

    public Role handleUpdateRole(Role roleFe) throws IdvalidException {
        Optional<Role> roleOptional = this.roleRepository.findById(roleFe.getId());
        if (roleOptional.isPresent()) {
            Role roleBe = roleOptional.get();
            if (roleFe.getName() != null) {
                roleBe.setName(roleFe.getName());
            }
            if (roleFe.getDescription() != null) {
                roleBe.setDescription(roleFe.getDescription());
            }
            if (roleFe.isActive()) {
                roleBe.setActive(roleFe.isActive());
            }
            // Lưu lại thay đổi vào cơ sở dữ liệu
            return roleRepository.save(roleBe);
        }
        throw new IdvalidException("Role này không tồn tại");
    }

    public Role fetchById(int id) throws IdvalidException {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        if (roleOptional.isPresent()){
            roleOptional.isPresent();
        }
        throw new IdvalidException("Role này không tồn tại");
    }

    public ResultPaginationDTO fetchAllRole(Specification<Role> spec, Pageable pageable){
        Page<Role> rolePage = this.roleRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(rolePage.getTotalPages());
        mt.setTotal(rolePage.getTotalElements());
        rs.setMeta(mt);

        List<ResRoleDTO> listRole = rolePage.getContent().stream().map(item -> new ResRoleDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isActive()
        )).collect(Collectors.toList());
        rs.setResult(rolePage);
        return rs;
    }

    public void deleteId(int id) throws IdvalidException {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        if (!roleOptional.isPresent()){
            throw new IdvalidException("role khong ton tai");
        }
        Role role = roleOptional.get();
        if (role.getUsers() !=null){
            role.setUsers(null);
            this.roleRepository.save(role);
        }
        this.roleRepository.deleteById(id);
    }

    public ResCreateRole convertToResRoleDTO(Role role){
        ResCreateRole res = new ResCreateRole();
        res.setId(role.getId());
        res.setName(role.getName());

        res.setCreatedAt(role.getCreatedAt());
        res.setUpdatedAt(role.getUpdatedAt());
        res.setCreatedBy(role.getCreatedBy());
        res.setUpdatedBy(role.getUpdatedBy());

        if (role.getUsers()!=null){
            List<ResCreateRole.UserRole> userRoleList = role.getUsers().stream()
                    .map(roleUser-> new ResCreateRole.UserRole(
                            roleUser.getId(),
                            roleUser.getName()
                    )).collect(Collectors.toList());
            res.setUserRole(userRoleList);

        }

        return res;


    }

}
