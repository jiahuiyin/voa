package Acitivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.example.voa.R;
import com.farsunset.cim.sdk.android.CIMEventListener;
import com.farsunset.cim.sdk.android.CIMListenerManager;
import com.farsunset.cim.sdk.android.CIMPushManager;
import com.farsunset.cim.sdk.android.model.Message;
import com.farsunset.cim.sdk.android.model.ReplyBody;
import com.farsunset.cim.sdk.android.model.SentBody;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Service.ChatMessage;
import Service.MyApplication;
import Service.MyStringRequest;
import Service.TeamMember;
import cim.http.SendMessageManager;
import jp.bassaer.chatmessageview.views.ChatView;

public class MessageActivity extends AppCompatActivity implements CIMEventListener {

    private ChatView mChatView;
    private String account;
    private TeamMember receiver;
    private MyApplication application;
    private Bitmap myIcon,yourIcon;
    private String myName,yourName;
    private RequestQueue mQueue;

    //private SendMessageDialog sendMessageDialog;

    //private RecyclerView messageListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        application = (MyApplication) this.getApplicationContext();
        mQueue= com.android.volley.toolbox.Volley.newRequestQueue(MessageActivity.this);
        mChatView=findViewById(R.id.chat_view);
        account=application.getId();
        receiver=(TeamMember)getIntent().getExtras().get("receiver");
        myIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //User name
        myName = application.getUser().getUsername();
        yourIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        yourName = receiver.getUsername();
        mChatView = (ChatView)findViewById(R.id.chat_view);
        //Set UI options
        mChatView.setRightBubbleColor(ContextCompat.getColor(this, R.color.main_orange_color));
        mChatView.setLeftBubbleColor(Color.WHITE);
        mChatView.setBackgroundColor(ContextCompat.getColor(this, R.color.blueGray500));
        mChatView.setSendButtonColor(ContextCompat.getColor(this, R.color.green500));
        mChatView.setSendIcon(R.drawable.ic_action_send);
        mChatView.setRightMessageTextColor(Color.WHITE);
        mChatView.setLeftMessageTextColor(Color.BLACK);
        mChatView.setUsernameTextColor(Color.WHITE);
        mChatView.setSendTimeTextColor(Color.WHITE);
        mChatView.setDateSeparatorColor(Color.WHITE);
        mChatView.setInputTextHint("new message...");
        getChatLog(new VolleyCallback() {
            @Override
            public void onSuccess(List<ChatMessage> l_message) {
                if(!l_message.isEmpty()) {
                    int i=0;
                    while(i<l_message.size()){
                        if(l_message.get(i).getSender().equals(application.getId())){
                            jp.bassaer.chatmessageview.models.Message sendMessage = new jp.bassaer.chatmessageview.models.Message.Builder()
                                    .setUserName(myName)
                                    .setRightMessage(true)
                                    .setMessageText(l_message.get(i).getContent())
                                    .build();
                            sendMessage.setTimeText("past");
                            mChatView.send(sendMessage);
                        }
                        else{
                            jp.bassaer.chatmessageview.models.Message receivedMessage = new  jp.bassaer.chatmessageview.models.Message.Builder()
                                    .setUserName(yourName)
                                    .setRightMessage(false)
                                    .setMessageText(l_message.get(i).getContent())
                                    .build();
                            receivedMessage.setTimeText("past");
                            mChatView.receive(receivedMessage);
                        }
                        i++;
                    }
                }
            }
        });
        mChatView.setOnClickSendButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mChatView.getInputText().isEmpty()){
                    Message message = new Message();
                    /*
                     * 暂定action为0
                     */
                    message.setAction("0");
                    message.setSender(account);
                    message.setReceiver(receiver.getId().trim());
                    message.setContent(mChatView.getInputText());
                    SendMessageManager.send(message,application.getToken());
                    jp.bassaer.chatmessageview.models.Message sendMessage = new jp.bassaer.chatmessageview.models.Message.Builder()
                            .setUserName(myName)
                            .setRightMessage(true)
                            .setMessageText(mChatView.getInputText())
                            .build();
                    //Set to chat view
                    mChatView.send(sendMessage);
                    //Reset edit text
                    mChatView.setInputText("");
                }
            }
        });
        CIMListenerManager.registerMessageListener(this);
    }

    @Override
    public void onMessageReceived(Message message) {
        if(message.getSender().equals(receiver.getId())) {
            final jp.bassaer.chatmessageview.models.Message receivedMessage = new jp.bassaer.chatmessageview.models.Message.Builder()
                    .setUserName(yourName)
                    .setRightMessage(false)
                    .setMessageText(message.getContent())
                    .build();
            mChatView.receive(receivedMessage);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /*
         * 页面关闭记得调用此方法喔
         */
        CIMListenerManager.removeMessageListener(this);

        /*
         * 退出应用记得调用此方法喔
         */
        CIMPushManager.destroy(this);
    }



    @Override
    public void onReplyReceived(ReplyBody replyBody) {

    }

    @Override
    public void onSendFinished(SentBody sentBody) {

    }

    @Override
    public void onNetworkChanged(NetworkInfo networkInfo) {

    }

    @Override
    public void onConnectFinished(boolean b) {

    }

    @Override
    public void onConnectionClosed() {
    }

    @Override
    public void onConnectFailed() {}

    @Override
    public int getEventDispatchOrder() {
        return 0;
    }

    private void getChatLog(final VolleyCallback callback){
        String url="https://voa.yinjiahui.cn/api/message?receiverId="+receiver.getId();
        MyStringRequest request = new MyStringRequest(
                JsonRequest.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            String jsonStr = response;
                            ObjectMapper mapper = new ObjectMapper();

                            JsonNode node = mapper.readTree(jsonStr);
                            String dataJson;
                            dataJson = node.get("data").toString();
                            List<ChatMessage>l_message = mapper.readValue(dataJson, new TypeReference<List<ChatMessage>>() {
                            });
                            String code = node.get("code").toString();
                            callback.onSuccess(l_message);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("3");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeader = new HashMap<String, String>();
                mHeader.put("Authorization", application.getToken());
                return mHeader;
            }

            @Override
            protected String getParamsEncoding() {
                return "GBK";
            }
        };
        mQueue.add(request);
    }

    public interface VolleyCallback {
        void onSuccess(List<ChatMessage> l_message);
    }
}