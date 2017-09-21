package com.codedrinker.controller;

import com.codedrinker.dto.IssueDTO;
import com.codedrinker.dto.UserDTO;
import com.codedrinker.entity.ResponseDTO;
import com.codedrinker.service.AuthorizationService;
import com.codedrinker.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by codedrinker on 06/09/2017.
 */
@Controller
public class IssueController extends BaseController {

    @Value("${github.client.id}")
    private String clientId;

    @Autowired
    private IssueService issueService;

    @Autowired
    private AuthorizationService authorizationService;

    @ResponseBody
    @RequestMapping(value = "/issues", method = RequestMethod.POST)
    public Object post(@RequestBody IssueDTO issueDTO) {
        ResponseDTO responseDTO = authorizationService.get(Integer.parseInt(issueDTO.getId()));
        if (responseDTO != null && responseDTO.getData() != null) {
            UserDTO data = (UserDTO) responseDTO.getData();
            if (data.getToken() == null || "".equals(data.getToken())) {
                return ResponseDTO.error("No Account? Register on https://commenthub.herokuapp.com");
            } else {
                ResponseDTO issue = issueService.createIssue(data.getToken(), issueDTO);
                return issue;
            }
        } else {
            return ResponseDTO.error("No Account? Register on https://commenthub.herokuapp.com");
        }
    }
}
