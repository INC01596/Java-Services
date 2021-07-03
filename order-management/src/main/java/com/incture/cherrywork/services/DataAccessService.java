//package com.incture.cherrywork.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//
//import com.incture.cherrywork.entities.UserDetail;
//import com.incture.cherrywork.repositories.IUserDetailRepository;
//import com.incture.cherrywork.util.HelperClass;
//
//public class DataAccessService {
//	
//	
//	@Autowired
//	private IUserDetailRepository userDetailRepository;
//	
//	
//	@Override
//	public ResponseEntity<Object> fetchPermisionDetailsForUser(String userId, String projectName) {
//
//		try {
//
//			if (!HelperClass.checkString(userId) && !HelperClass.checkString(projectName)) {
//
//				// Fetching the user's basic details by userId
//				UserDetail userDetails = userDetailRepository.findByUserIdAndProject(userId, projectName);
//
//				if (userDetails != null) {
//
//					PermissionObject permissionObj = permissionObjectRepository
//							.findByPermissionId(userDetails.getPermissionId());
//
//					if (permissionObj != null) {
//
//						List<PermissionDetail> permissionDetailsList = permissionDetailRepository
//								.findByPermissionId(permissionObj);
//
//						if (permissionDetailsList != null && !permissionDetailsList.isEmpty()) {
//
//							return new ResponseEntity<>(permissionDetailsList, HttpStatus.OK);
//
//						} else {
//							return new ResponseEntity<>(
//									"Permission object is assigned but doesn't have any assignment to attributes for user : "
//											+ userId,
//									HttpStatus.CONFLICT);
//						}
//
//					} else {
//						return new ResponseEntity<>("Permission object is not assigned to user : " + userId,
//								HttpStatus.CONFLICT);
//					}
//				} else {
//					return new ResponseEntity<>("User not found with the id : " + userId, HttpStatus.CONFLICT);
//				}
//			} else {
//				return new ResponseEntity<>(
//						AppErrorMsgConstants.INVALID_INPUT + "Please provide user id and project name.",
//						HttpStatus.BAD_REQUEST);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<>(AppErrorMsgConstants.EXCEPTION_POST_MSG + e.getMessage(),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}
//
//
//
//}
