import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class GetMyReviews extends Operation {
  @Override
  public String run(Context ctx) {

    Result<List<Review>> result = ReviewRepository.getReviewsByUsername(ctx.user.name);
    if(result.err != null) return result.err;

    List<Review> reviews = result.value;

    return new Success(reviews).toJson();
  }

  private class Success extends JsonSerializable {
    final public String status = "200";

    final public String mensagem = "Operação executada com sucesso!";

    public List<Review> reviews;

    Success(List<Review> reviews) {
      this.reviews = reviews;
    }
  }
}
