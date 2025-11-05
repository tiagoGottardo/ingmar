import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

class CreateReview extends Operation {
  @Override
  public String run(Context ctx) {
    Review review = Review.fromJson(ctx.request.get("review"));

    review.username = ctx.user.name;

    String err = ReviewRepository.createReview(review);
    if(err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "201";

    @JsonProperty
    final private String mensagem = "Review criada com sucesso!";
  }
}
