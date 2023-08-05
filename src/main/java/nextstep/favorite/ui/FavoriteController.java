package nextstep.favorite.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nextstep.auth.principal.AuthenticationPrincipal;
import nextstep.auth.principal.UserPrincipal;
import nextstep.favorite.application.FavoriteService;
import nextstep.favorite.application.dto.FavoriteResponse;
import nextstep.favorite.application.dto.FavoriteRequest;

@RestController
public class FavoriteController {

	private final FavoriteService favoriteService;

	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	@PostMapping("/favorites")
	public ResponseEntity<Void> create(@RequestBody FavoriteRequest favoriteRequest,
		@AuthenticationPrincipal UserPrincipal userPrincipal) {
		Long savedId = favoriteService.create(favoriteRequest, userPrincipal.getUsername());
		return ResponseEntity.created(URI.create("/favorites/" + savedId)).build();
	}

	@GetMapping("/favorites")
	public ResponseEntity<List<FavoriteResponse>> showFavorites(@AuthenticationPrincipal UserPrincipal userPrincipal) {
		return ResponseEntity.ok().body(favoriteService.findAllByMemberEmail(userPrincipal.getUsername()));
	}
}
