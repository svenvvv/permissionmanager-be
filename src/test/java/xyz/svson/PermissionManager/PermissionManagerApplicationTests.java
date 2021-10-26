package xyz.svson.PermissionManager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * I've never written Java tests and botched a few attempts, so I'll just outline what I'd test ;).
 *
 * When I refer to "Common tests", then I mean the following by that:
 * 	*) Good data, must accept,
 * 	*) No POST body, must reject,
 * 	*) Empty JSON POST body, must reject,
 *
 * PermissionController:
 * 	*) createPermission():
 * 		*) Common tests,
 * 		*) 2 char title, must reject,
 * 		*) >3 char title, but 2 non-whitespace chars, must reject.
 * 		*) Very long title and subtitle, must reject and must not crash (currenly unchecked).
 * PermissionNodeController:
 * 	*) createNode():
 * 		*) Common tests,
 * 		*) Invalid permission ID, must reject,
 * 		*) Text as permission ID, must reject.
 * 	*) setNodeParent():
 * 		*) Common tests,
 * 		*) Null parent ID (adopt to root node), must accept,
 * 		*) Invalid parent ID, must reject,
 * 		*) Invalid child ID, must reject,
 * 		*) Either ID as text, must reject.
 * 	*) deleteNode():
 * 		*) Common tests,
 * 		*) Invalid node ID, must reject.
 *
 */

@SpringBootTest
class PermissionManagerApplicationTests {

	@Test
	void contextLoads() {
	}

}
