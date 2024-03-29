// Copyright 2009 Google Inc.
package com.google.appengine.demos.mandelbrot;

/**
 * {@code Palette} converts double values into colors in such a way
 * that the difference between two input values is reflected in the
 * difference between the colors that are returned.
 *
 */
public class Palette {
  private static final int PALETTE[] = new int[] {
    0x800000, 0x800101, 0x810303, 0x820505, 0x830707, 0x840909, 0x850b0b, 0x860d0d,
    0x870f0f, 0x881111, 0x891313, 0x8a1515, 0x8b1717, 0x8c1919, 0x8d1b1b, 0x8e1d1d,
    0x8f1f1f, 0x902121, 0x912323, 0x922525, 0x932727, 0x942929, 0x952b2b, 0x962d2d,
    0x972f2f, 0x983131, 0x993333, 0x9a3535, 0x9b3737, 0x9c3939, 0x9d3b3b, 0x9e3d3d,
    0x9f3f3f, 0xa04141, 0xa14343, 0xa24545, 0xa34747, 0xa44949, 0xa54b4b, 0xa64d4d,
    0xa74f4f, 0xa85151, 0xa95353, 0xaa5555, 0xab5757, 0xac5959, 0xad5b5b, 0xae5d5d,
    0xaf5f5f, 0xb06161, 0xb16363, 0xb26565, 0xb36767, 0xb46969, 0xb56b6b, 0xb66d6d,
    0xb76f6f, 0xb87171, 0xb97373, 0xba7575, 0xbb7777, 0xbc7979, 0xbd7b7b, 0xbe7d7d,
    0xbf7f7f, 0xc08181, 0xc18383, 0xc28585, 0xc38787, 0xc48989, 0xc58b8b, 0xc68d8d,
    0xc78f8f, 0xc89191, 0xc99393, 0xca9595, 0xcb9797, 0xcc9999, 0xcd9b9b, 0xce9d9d,
    0xcf9f9f, 0xd0a1a1, 0xd1a3a3, 0xd2a5a5, 0xd3a7a7, 0xd4a9a9, 0xd5abab, 0xd6adad,
    0xd7afaf, 0xd8b1b1, 0xd9b3b3, 0xdab5b5, 0xdbb7b7, 0xdcb9b9, 0xddbbbb, 0xdebdbd,
    0xdfbfbf, 0xe0c1c1, 0xe1c3c3, 0xe2c5c5, 0xe3c7c7, 0xe4c9c9, 0xe5cbcb, 0xe6cdcd,
    0xe7cfcf, 0xe8d1d1, 0xe9d3d3, 0xead5d5, 0xebd7d7, 0xecd9d9, 0xeddbdb, 0xeedddd,
    0xefdfdf, 0xf0e1e1, 0xf1e3e3, 0xf2e5e5, 0xf3e7e7, 0xf4e9e9, 0xf5ebeb, 0xf6eded,
    0xf7efef, 0xf8f1f1, 0xf9f3f3, 0xfaf5f5, 0xfbf7f7, 0xfcf9f9, 0xfdfbfb, 0xfefdfd,
    0xffffff, 0xfdfeff, 0xfcfdff, 0xfafcff, 0xf9fbff, 0xf7faff, 0xf6f9ff, 0xf4f8ff,
    0xf3f7ff, 0xf1f6ff, 0xf0f5ff, 0xeef4ff, 0xedf3ff, 0xebf2ff, 0xeaf1ff, 0xe8f0ff,
    0xe7efff, 0xe5eeff, 0xe4edff, 0xe2ecff, 0xe1ebff, 0xdfeaff, 0xdee9ff, 0xdce8ff,
    0xdbe7ff, 0xd9e6ff, 0xd8e5ff, 0xd6e4ff, 0xd5e3ff, 0xd3e2ff, 0xd2e1ff, 0xd0e0ff,
    0xcfdfff, 0xcddeff, 0xccddff, 0xcadcff, 0xc9dbff, 0xc7daff, 0xc6d9ff, 0xc4d8ff,
    0xc3d7ff, 0xc1d6ff, 0xc0d5ff, 0xbed4ff, 0xbdd3ff, 0xbbd2ff, 0xbad1ff, 0xb8d0ff,
    0xb7cfff, 0xb5ceff, 0xb4cdff, 0xb2ccff, 0xb1cbff, 0xafcaff, 0xaec9ff, 0xacc8ff,
    0xabc7ff, 0xa9c6ff, 0xa8c5ff, 0xa6c4ff, 0xa5c3ff, 0xa3c2ff, 0xa2c1ff, 0xa0c0ff,
    0x9fbfff, 0x9dbeff, 0x9cbdff, 0x9abcff, 0x99bbff, 0x97baff, 0x96b9ff, 0x94b8ff,
    0x93b7ff, 0x91b6ff, 0x90b5ff, 0x8eb4ff, 0x8db3ff, 0x8bb2ff, 0x8ab1ff, 0x88b0ff,
    0x87afff, 0x85aeff, 0x84adff, 0x82acff, 0x81abff, 0x7faaff, 0x7ea9ff, 0x7ca8ff,
    0x7ba7ff, 0x79a6ff, 0x78a5ff, 0x76a4ff, 0x75a3ff, 0x73a2ff, 0x72a1ff, 0x70a0ff,
    0x6f9fff, 0x6d9eff, 0x6c9dff, 0x6a9cff, 0x699bff, 0x679aff, 0x6699ff, 0x6498ff,
    0x6397ff, 0x6196ff, 0x6095ff, 0x5e94ff, 0x5d93ff, 0x5b92ff, 0x5a91ff, 0x5890ff,
    0x578fff, 0x558eff, 0x548dff, 0x528cff, 0x518bff, 0x4f8aff, 0x4e89ff, 0x4c88ff,
    0x4b87ff, 0x4986ff, 0x4885ff, 0x4684ff, 0x4583ff, 0x4382ff, 0x4281ff, 0x4080ff,
    0x3f7fff, 0x3f7efd, 0x407dfb, 0x407cf9, 0x417bf7, 0x417af5, 0x4279f3, 0x4278f1,
    0x4377ef, 0x4376ed, 0x4475eb, 0x4474e9, 0x4573e7, 0x4572e5, 0x4671e3, 0x4670e1,
    0x476fdf, 0x476edd, 0x486ddb, 0x486cd9, 0x496bd7, 0x496ad5, 0x4a69d3, 0x4a68d1,
    0x4b67cf, 0x4b66cd, 0x4c65cb, 0x4c64c9, 0x4d63c7, 0x4d62c5, 0x4e61c3, 0x4e60c1,
    0x4f5fbf, 0x4f5ebd, 0x505dbb, 0x505cb9, 0x515bb7, 0x515ab5, 0x5259b3, 0x5258b1,
    0x5357af, 0x5356ad, 0x5455ab, 0x5454a9, 0x5553a7, 0x5552a5, 0x5651a3, 0x5650a1,
    0x574f9f, 0x574e9d, 0x584d9b, 0x584c99, 0x594b97, 0x594a95, 0x5a4993, 0x5a4891,
    0x5b478f, 0x5b468d, 0x5c458b, 0x5c4489, 0x5d4387, 0x5d4285, 0x5e4183, 0x5e4081,
    0x5f3f7f, 0x603e7d, 0x603d7b, 0x613c79, 0x613b77, 0x623a75, 0x623973, 0x633871,
    0x63376f, 0x64366d, 0x64356b, 0x653469, 0x653367, 0x663265, 0x663163, 0x673061,
    0x672f5f, 0x682e5d, 0x682d5b, 0x692c59, 0x692b57, 0x6a2a55, 0x6a2953, 0x6b2851,
    0x6b274f, 0x6c264d, 0x6c254b, 0x6d2449, 0x6d2347, 0x6e2245, 0x6e2143, 0x6f2041,
    0x6f1f3f, 0x701e3d, 0x701d3b, 0x711c39, 0x711b37, 0x721a35, 0x721933, 0x731831,
    0x73172f, 0x74162d, 0x74152b, 0x751429, 0x751327, 0x761225, 0x761123, 0x771021,
    0x770f1f, 0x780e1d, 0x780d1b, 0x790c19, 0x790b17, 0x7a0a15, 0x7a0913, 0x7b0811,
    0x7b070f, 0x7c060d, 0x7c050b, 0x7d0409, 0x7d0307, 0x7e0205, 0x7e0103, 0x7f0001,
  };

  /**
   * Returns a color that corresponds to the specified value.
   *
   * @param value an arbitrary floating-point value
   * @return a color value as produced by {@color ColorUtil}
   */
  public int getColor(double value) {
    int index = (int) value;
    double remainder = value - Math.floor(value);

    int from = PALETTE[index % PALETTE.length];
    int to = PALETTE[(index+1) % PALETTE.length];

    int r = ColorUtil.red(from) +
        (int) ((ColorUtil.red(to) - ColorUtil.red(from)) * remainder);
    int g = ColorUtil.green(from) +
        (int) ((ColorUtil.green(to) - ColorUtil.green(from)) * remainder);
    int b = ColorUtil.blue(from) +
        (int) ((ColorUtil.blue(to) - ColorUtil.blue(from)) * remainder);
    return ColorUtil.create(b, g, r);
  }
}
