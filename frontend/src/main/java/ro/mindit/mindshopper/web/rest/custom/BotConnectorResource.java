package ro.mindit.mindshopper.web.rest.custom;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.google.gson.JsonObject;
import com.microsoft.bot.schema.models.Activity;
import com.microsoft.bot.schema.models.ActivityTypes;
import com.microsoft.bot.schema.models.ChannelAccount;
import com.microsoft.bot.schema.models.ConversationAccount;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mindit.mindshopper.chatbot.ObjectMapperFactory;
import ro.mindit.mindshopper.domain.Cart;
import ro.mindit.mindshopper.domain.ItemCart;
import ro.mindit.mindshopper.service.custom.CartService;
import ro.mindit.mindshopper.service.custom.ItemCartService;
import ro.mindit.mindshopper.service.dto.custom.BotStartResponseDTO;
import ro.mindit.mindshopper.service.dto.custom.RequestItemToCartDTO;

import static ro.mindit.mindshopper.web.rest.util.Common.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class BotConnectorResource {
    private static final Logger LOG = LoggerFactory.getLogger(BotConnectorResource.class);

    @Autowired
    private ItemCartService itemCartService;
    @Autowired
    private CartService cartService;

    @GetMapping(value = "/start", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity startBotMessage() throws URISyntaxException, IOException {
        ObjectMapper mapper = ObjectMapperFactory.createObjectMapper();
        URI uri = new URIBuilder()
            .setScheme(HTTP_SCHEME)
            .setHost(HOST_NAME)
            .setPath(HOST_API)
//            .setParameter("user_id", null)
            .build();
        LOG.info("Requesting URI: " + uri.toString());

        Activity aa = new Activity();
        aa.withType(ActivityTypes.MESSAGE);
        aa.withText("Test");
        aa.withName("Test1");

        ChannelAccount a = new ChannelAccount();

        JsonNode userIdNode = mapper.valueToTree("TestUserId");
        a.setProperties("userId", userIdNode);

        JsonNode userNameNode = mapper.valueToTree("TestUserName");
        a.setProperties("name", userNameNode);

        JsonNode cartIdNode = mapper.valueToTree("CartId");
        a.setProperties("cartId", cartIdNode);

        HttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader("Authorization", "Bearer "+ SECRET_KEY);
        httpPost.setHeader("Accept", "application/json");

        aa.withFrom(a);
//        StringEntity entity = new StringEntity(aa.toString());
        String actStr = mapper.writeValueAsString(aa);
        HttpEntity httpEntity = new ByteArrayEntity(actStr.getBytes("UTF-8"));
//        httpPost.setEntity(httpEntity);


        HttpResponse resp = client.execute(httpPost);
        LOG.info("Status CODE: " + resp.getStatusLine().getStatusCode());


        BotStartResponseDTO responseDTO = mapper.readValue(resp.getEntity().getContent(), BotStartResponseDTO.class);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(value = "/message")
    public ResponseEntity getBotMessage() {

        return null;
    }

    @PostMapping(value = "/message")
    public ResponseEntity postBotMessage() {

        return null;
    }

    @GetMapping(value = "/carts/{cartId}/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getItemsByCart(@Param("cartId") Long cartId) {
        List<ItemCart> items =  itemCartService.findByCartId(cartId);

        return ResponseEntity.ok(items);
    }

    @PutMapping(value = "/carts/add-item")
    public ResponseEntity putItemCartToCart(RequestItemToCartDTO itemToCartDTO) {
        Optional<Cart> optionalCart = cartService.findById(itemToCartDTO.getCartId());
        if (!optionalCart.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Cart cart = optionalCart.get();
        Set<ItemCart> items = cart.getItems();
        items.add(itemToCartDTO.getItemCart());

        cart.setItems(items);
        return ResponseEntity.ok(cartService.save(cart));
    }

    @DeleteMapping(value = "/delete-from")
    private ResponseEntity deleteItemFromCart(RequestItemToCartDTO itemToCartDTO) {
        Optional<Cart> optionalCart = cartService.findById(itemToCartDTO.getCartId());
        if (!optionalCart.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cart cart = optionalCart.get();
        Set<ItemCart> items = cart.getItems();
        if(!items.remove(itemToCartDTO.getItemCart())) {
            return ResponseEntity.notFound().build();
        }

        cart.setItems(items);
        return ResponseEntity.ok(cartService.save(cart));
    }
}
