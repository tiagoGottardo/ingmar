import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

class DeleteReview extends Operation {
  @Override
  public String run(Context ctx) {
    Review newData = Review.fromJson(ctx.request.get("review"));

    Result<Review> result = ReviewRepository.getReviewById(ctx.request.get("id").asInt());
    if(result.err != null) return result.err;

    Review review = result.value;

    if(!review.username.equals(ctx.user.name) && ctx.user.isAdmin())
      return Failed.response(403);

    String err = ReviewRepository.deleteReview(review.id); 
    if(err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "200";

    @JsonProperty
    final private String mensagem = "Operação executada com sucesso!";
  }
}
