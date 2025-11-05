import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mindrot.jbcrypt.BCrypt;

class UpdateReview extends Operation {
  @Override
  public String run(Context ctx) {
    Review newData = Review.fromJson(ctx.request.get("review"));

    Result<Review> result = ReviewRepository.getReviewById(newData.id);
    if(result.err != null) return result.err;

    Review review = result.value;

    if(!review.username.equals(ctx.user.name))
      return Failed.response(403);

    review.updateReview(newData);

    String err = ReviewRepository.updateReview(review); 
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
