package cn.kanejin.olla.request;

/**
 * @author Kane Jin
 */
public class AdminRequester implements Requester {

    private Long id;

    private String name;

    private String ip;

    public AdminRequester(Long adminId, String adminName, String ip) {
        this.id = adminId;
        this.name = adminName;
        this.ip = ip;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public RequesterLevel getLevel() {
        return RequesterLevel.ADMIN;
    }

    @Override
    public String toString() {
        return "{id:" + getId() + ", level:" + getLevel() + "}";
    }
}
