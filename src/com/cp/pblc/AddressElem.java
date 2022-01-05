package com.cp.pblc;

/**
 * @description: 临时补充的非终结符M
 * @author: LuBixing
 * @date: 2022/1/6 2:15
 */
public class AddressElem {
    private int quad;
    public AddressElem(){}
    public AddressElem(int quad){
        this.quad = quad;
    }

    public int getQuad(){
        return quad;
    }
    public void setQuad(int quad){
        this.quad = quad;
    }
}
