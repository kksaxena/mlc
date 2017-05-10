package trainedge.multilingualchat.adapters;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import java.util.List;

import trainedge.multilingualchat.R;
import trainedge.multilingualchat.models.Chat;


public class ChatRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_ME = 1;
    private static final int VIEW_TYPE_OTHER = 2;
    private final String language;

    private List<Chat> mChats;

    public ChatRecyclerAdapter(List<Chat> chats, String language) {
        mChats = chats;
        this.language = language;
    }

    public void add(Chat chat) {
        mChats.add(chat);
        notifyItemInserted(mChats.size() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new MyChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (TextUtils.equals(mChats.get(position).senderUid,
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            configureMyChatViewHolder((MyChatViewHolder) holder, position);
        } else {
            configureOtherChatViewHolder((OtherChatViewHolder) holder, position);
        }
    }

    private void configureMyChatViewHolder(MyChatViewHolder myChatViewHolder, int position) {
        Chat chat = mChats.get(position);

        String alphabet = chat.sender.substring(0, 1);

        myChatViewHolder.txtChatMessage.setText(chat.message);
        myChatViewHolder.txtUserAlphabet.setText(alphabet);
    }

    private void configureOtherChatViewHolder(final OtherChatViewHolder otherChatViewHolder, int position) {
        final Chat chat = mChats.get(position);

        final String alphabet = chat.sender.substring(0, 1);
        class bgStuff extends AsyncTask<String, Void, String> {

            String translatedText = "";


            @Override
            protected String doInBackground(String... params) {
                try {
                    translatedText = translate(params[0]);
                } catch (Exception e) {
                    translatedText = params[0];
                }
                return translatedText;
            }

            @Override
            protected void onPostExecute(String result) {
                otherChatViewHolder.txtChatMessage.setText(translatedText);
                otherChatViewHolder.txtUserAlphabet.setText(alphabet);
            }

        }

        new bgStuff().execute(chat.message);

    }


    public String translate(String text) throws Exception {
        // Set the Client ID / Client Secret once per JVM. It is set statically and applies to all services
        Translate.setClientId("zaid");
        Translate.setClientSecret("ea86bbbbb7c34f1f859e8425d1ed32ca");

        String translatedText = "";

        // English AUTO_DETECT -> gERMAN Change this if u wanna other languages

        translatedText = Translate.execute(text, Language.fromString(language));

        return translatedText;
    }

    @Override
    public int getItemCount() {
        if (mChats != null) {
            return mChats.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.equals(mChats.get(position).senderUid,
                FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    private static class MyChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage, txtUserAlphabet;

        public MyChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }

    private static class OtherChatViewHolder extends RecyclerView.ViewHolder {
        private TextView txtChatMessage, txtUserAlphabet;

        public OtherChatViewHolder(View itemView) {
            super(itemView);
            txtChatMessage = (TextView) itemView.findViewById(R.id.text_view_chat_message);
            txtUserAlphabet = (TextView) itemView.findViewById(R.id.text_view_user_alphabet);
        }
    }
}
