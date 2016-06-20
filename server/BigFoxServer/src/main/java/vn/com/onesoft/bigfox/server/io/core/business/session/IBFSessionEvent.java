/*
 * Author: QuanPH
 * Copyright @ 2015 by OneSoft.,JSC
 * 
 */
package vn.com.onesoft.bigfox.server.io.core.business.session;

/**
 *
 * @author QuanPH
 */
public interface IBFSessionEvent {

    /**
     * Gọi một lần duy nhất khi người dùng mở ứng dụng
     *
     * @param session
     */
    public void startSession(IBFSession session);

    /**
     * Gọi mỗi lần kết nối lại thành công. Kết nối lại chỉ diễn ra khi thoả mãn
     * tất cả các điều kiện 1. Ứng dụng chưa đóng 2. Mạng bị mất kết nối sau đó
     * có kết nối trở lại và server nhận được sự kiện kết nối mới trở lại trước
     * khi nhận sự kiện kết nối cũ đóng, khi đó sự kiện removeSession sẽ không
     * được gọi
     *
     * @param session - session mới = session cũ không thay đổi
     */
    public void reconnectSession(IBFSession session);

    /**
     * Sự kiện được gọi khi 1. Mất kết nối mà kết nối mới chưa được thiết lập
     * hoặc được thiết lập nhưng server nhận được sự kiện kết nối mới sau sự
     * kiện mất kết nối, khi đó dù kết nối mới đến vẫn tạo Session mới, và sự
     * kiện reconnectSession sẽ không được gọi 2. Thời gian timeout vượt quá
     * giới hạn
     *
     * @param session - Session cũ
     */
    public void removeSession(IBFSession session);

    /**
     * Sự kiện được gọi nếu cứ sau 1 giây server không nhận được bản tin nào từ phía client 
     * @param session
     * @param delaySecond 
     */
    public void onDelay(IBFSession session, int delaySecond);
}
