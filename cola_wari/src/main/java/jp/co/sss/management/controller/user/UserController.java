package jp.co.sss.management.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.persistence.EntityManager;
import jp.co.sss.management.entity.AgendaEntry;
import jp.co.sss.management.entity.User;

@Controller
public class UserController {

	@Autowired
	jp.co.sss.management.repository.UserRepository userRepository;
	
	@Autowired
	jp.co.sss.management.repository.AgendaEntryRepository agendaEntryRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@GetMapping({ "/user/list" })
	public String userList(Model model) {
		List<User> usersList = userRepository.findByStatus(0);
		model.addAttribute("users", usersList);

		return "user/list";
	}
	
	@GetMapping({"/user/detail/{id}"})
	@PostMapping({"/user/detail/{id}"})
	public String userDetail(Model model,@PathVariable Integer id) {
		User user = userRepository.findByUserId(id);
		model.addAttribute("users",user);
		System.out.println(model.getAttribute("users"));
		List<AgendaEntry> progressEntry = agendaEntryRepository.findByUserId(id,0);
		model.addAttribute("progresses",progressEntry);
		List<AgendaEntry> endEntry = agendaEntryRepository.findByUserId(id,1);
		model.addAttribute("ends",endEntry);
		System.out.println("endの処理できてます！！");
		
		return "user/detail";
	}
	
}

