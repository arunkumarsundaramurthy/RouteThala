package com.routethala.aks.routethala;

import android.graphics.Bitmap;

import java.util.List;

public interface VisionAPI {
    public List<TextDetails> identifyBus(Bitmap bitmap);
}
